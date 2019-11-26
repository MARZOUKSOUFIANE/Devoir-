import { Component, OnInit } from '@angular/core';
import {CatalogueService} from '../services/catalogue.service';

@Component({
  selector: 'app-admin-products',
  templateUrl: './admin-products.component.html',
  styleUrls: ['./admin-products.component.css']
})
export class AdminProductsComponent implements OnInit {

  public analyses;
  public mode:string='list-products';
  public currentAnalyse;
  private style:string=undefined;
  private message:string=undefined;
  private categories;

  constructor(private catalService:CatalogueService) { }

  ngOnInit() {
    this.getAllProducts();
    this.style=undefined;
    this.message=undefined;
  }


    getAllProducts(){
      this.analyses=this.catalService.getRessource(this.catalService.host+"/analyses").subscribe(
        data=> {
          this.analyses = data;
        },err=>{
          console.log(err);
        })
    }

  onDeleteProduct(analyse) {
    this.catalService.deleteRessource(analyse._links.self.href).subscribe(
      data=>{
        this.getAllProducts();
      },err=>{
        console.log(err)
      }
    )
  }

  onEditProduct(analyse) {
    this.catalService.getRessource(analyse._links.self.href).subscribe(
      data=>{
        this.currentAnalyse=data;
      }
    )
    this.mode="edit-product"
  }

  onSaveProductEdit(value) {
    this.catalService.patchProduct(this.currentAnalyse._links.self.href,value).subscribe(
      data=>{
        this.getAllProducts();
        this.mode="list-products"
      },err=>{
        console.log(err);
      }
    )
  }

  onSaveProduct(value) {
    this.catalService.saveProduct(value).subscribe(
      data=>{
        console.log(value)
        this.getAllProducts();
        this.mode="list-products"
      },err=>{
        console.log(err);
      }
    )
  }

  onNewProduct() {
    this.mode="new-product"
  }
}

