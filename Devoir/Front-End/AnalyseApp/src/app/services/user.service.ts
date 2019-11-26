import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthenticationService} from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  public host:string="http://localhost:8081";

  constructor(private http:HttpClient,private authService:AuthenticationService) { }



  getAllUsers() {
    let header=new HttpHeaders({'Authorization':'Bearer '+this.authService.jwt});
    return this.http.get(this.host+"/appUsers",{headers:header});
  }

  deleteUser(url) {
    let header=new HttpHeaders({'Authorization':'Bearer '+this.authService.jwt});
    return this.http.delete(url,{headers:header});
  }

  saveUser(user) {
    let header=new HttpHeaders({'Authorization':'Bearer '+this.authService.jwt});
    return this.http.post(this.host+"/register",user,{headers:header})
  }

  getRessource(url) {
    let header=new HttpHeaders({'Authorization':'Bearer '+this.authService.jwt});
    return this.http.get(url,{headers:header});
  }

  saveUserEdit(user, url) {
    let header=new HttpHeaders({'Authorization':'Bearer '+this.authService.jwt});
    return this.http.patch(url,user,{headers:header})
  }

  addUserRole(value) {
    let header=new HttpHeaders({'Authorization':'Bearer '+this.authService.jwt});
    return this.http.post(this.host+"/addRole",value,{headers:header})
  }

  changeUserPassword(value) {
    let header=new HttpHeaders({'Authorization':'Bearer '+this.authService.jwt});
    return this.http.post(this.host+"/changePassword",value,{headers:header});
  }
}
