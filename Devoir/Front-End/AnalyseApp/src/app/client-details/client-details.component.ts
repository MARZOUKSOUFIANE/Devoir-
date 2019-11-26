import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpEventType, HttpResponse} from "@angular/common/http";
import {CatalogueService} from "../services/catalogue.service";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {AuthenticationService} from "../services/authentication.service";

@Component({
  selector: 'app-client-details',
  templateUrl: './client-details.component.html',
  styleUrls: ['./client-details.component.css']
})
export class ClientDetailsComponent implements OnInit {

  public host:string="http://localhost:8888";
  public client;
  public mode: string="list-products";
  public url: string;

  public selectedFiles: any;
  public editPhoto: boolean;
  public progress: number;
  public currentUploadedFile: any;
  public timestamp: number=0;

  constructor(private http:HttpClient,private catalService:CatalogueService,private activatedRoute:ActivatedRoute,
              private router:Router,private catalogueService:CatalogueService,private authService:AuthenticationService) {
    router.events.subscribe(event=>{
      if(event instanceof NavigationEnd){
        let param:string=activatedRoute.snapshot.params.url;
        let url=atob(param);
        this.getClient(url);
        this.url=url;
      }
    })
  }

  ngOnInit() {
  }

  private getClient(url) {
    this.catalService.getRessource(url).subscribe(
      data=>{
        this.client=data;
      },err=>{console.log(err)}
    )
  }

  onSelectedFile(event) {
    this.selectedFiles=event.target.files;
  }

  uploadPhoto() {
    this.progress=0;
    this.currentUploadedFile=this.selectedFiles.item(0);
    this.catalogueService.uploadPhotoProduct(this.currentUploadedFile,this.client.id).subscribe(event=>{
      if(event.type==HttpEventType.UploadProgress){
        this.progress=Math.round(100*event.loaded/event.total);
      }else if (event instanceof HttpResponse){
        //alert("Upload est termine avec succe");
        //this.getProducts("/products/search/selectedProducts");
        //window.location.reload();
        this.timestamp=Date.now();
      }
    },err=>{
      alert("problem de chargement... ");
    });
  }

  getTs() {
    return this.timestamp;
  }

  isAdmin() {
    return this.authService.isAdmin();
  }

  isManger() {
    return this.authService.isManager();
  }
}
