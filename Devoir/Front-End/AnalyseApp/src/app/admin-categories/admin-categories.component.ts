import { Component, OnInit } from '@angular/core';
import {CatalogueService} from '../services/catalogue.service';

@Component({
  selector: 'app-admin-categories',
  templateUrl: './admin-categories.component.html',
  styleUrls: ['./admin-categories.component.css']
})
export class AdminCategoriesComponent implements OnInit {
  public clients;
  public mode:string='list-cat';
  public currentClient;

  constructor(private catalService:CatalogueService) { }

  ngOnInit() {
    this.getAllGategories()
  }

  getAllGategories(){
    this.catalService.getAllCategoriee().
    subscribe(data=>{
      this.clients=data;
    },err=>{
      console.log(err)
    })
  }

  onDeleteCat(client) {
    let c = confirm("Etes vous sure ?")
    if (c) {
      this.catalService.deleteRessource(client._links.self.href).subscribe(
        data => {
          this.getAllGategories();
        }, err => {
          console.log(err)
        }
      )
    }
  }

  onNewCategorcie() {
    this.mode='new-cat'
  }

  onSaveCat(value) {
    this.catalService.saveCatal(value).
      subscribe(data=>{
      this.getAllGategories()
      this.mode='list-cat'
      window.location.reload();
    },err=>{
        console.log(err)
    })
  }

  onEditCat(cat) {
    this.mode='edit-cat'
    this.catalService.getRessource(cat._links.self.href).
      subscribe(data=>{
        this.currentClient=data;
    },err=>{
        console.log(err);
    })
  }

  onSaveCatEdit(value) {
    this.catalService.saveCatEdit(value,this.currentClient._links.self.href).
      subscribe(data=>{
        this.getAllGategories();
        this.mode='list-cat'
        window.location.reload();
    })
  }
}
