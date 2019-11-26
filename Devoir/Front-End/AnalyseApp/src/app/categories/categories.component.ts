import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CatalogueService} from '../services/catalogue.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {
  public host:string="http://localhost:8888"
  public clients;
  public products;
  public currentClient;


  constructor(private http:HttpClient,private catalService:CatalogueService,private router:Router) { }

  ngOnInit() {
    this.catalService.getAllCategoriee().subscribe(
      data=>{
        this.clients=data;
      },
      err => {
        console.log(err)
      }
    )
  }


 /* onGetProducts(cat) {
    this.currentCategorie=cat;
    let url=cat._links.self.href;
    this.router.navigate(['/products/'+btoa(url)]);
  }*/

  onGetAnalyseClient(client) {
    this.currentClient=client;
    let url=client._links.self.href;
    this.router.navigate(['/products/'+btoa(url)]);  }
}
