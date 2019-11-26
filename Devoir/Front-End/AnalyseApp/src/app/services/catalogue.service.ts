import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpHeaders, HttpRequest} from '@angular/common/http';
import {AuthenticationService} from './authentication.service';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CatalogueService {
  public host:string="http://localhost:8888";


  constructor(private http:HttpClient,private authService:AuthenticationService) { }

  getAllCategoriee(){
    return this.http.get(this.host+"/clients");
  }

  getRessource(url){
    return this.http.get(url);
  }

  deleteRessource(url) {
    let header=new HttpHeaders({'Authorization':'Bearer '+this.authService.jwt});
    return this.http.delete(url,{headers:header});
  }

  saveCatal(client) {
    let header=new HttpHeaders({'Authorization':'Bearer '+this.authService.jwt});
    return this.http.post(this.host+"/clients",client,{headers:header});
  }

  saveCatEdit(cat, url) {
    let header=new HttpHeaders({'Authorization':'Bearer '+this.authService.jwt});
    return this.http.patch(url,cat,{headers:header});
  }

  patchProduct(url, value) {
    let header=new HttpHeaders({'Authorization':'Bearer '+this.authService.jwt});
    return this.http.patch(url,value,{headers:header})
  }

      saveProduct(value) {
      let header=new HttpHeaders({'Authorization':'Bearer '+this.authService.jwt});
      return this.http.post(this.host+"/analyses",value,{headers:header});
  }

  getProduitsPage(currentPage, size) {
    return this.http.get(this.host+"/analyses?page="+currentPage+"&size="+size);
  }

  getClientsPage(currentPage: number, size: number) {
    return this.http.get(this.host+"/clients?page="+currentPage+"&size="+size);
  }

  uploadPhotoProduct(file: File,idClient) : Observable<HttpEvent<{}>>{
    let formData:FormData=new FormData();
    formData.append('file',file);
    const  req=new HttpRequest('POST',this.host+'/uploadPhoto/'+idClient,formData,{
      reportProgress:true,
      responseType:'text'
    });
    return this.http.request(req);
  }
}
