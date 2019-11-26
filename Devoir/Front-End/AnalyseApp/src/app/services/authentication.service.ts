import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  public host:string="http://localhost:8081";
  public jwt:string;
  public username:string;
  public roles:Array<string>;


  constructor(private http:HttpClient) { }

  login(data){
    return this.http.post(this.host+"/login",data,{observe:'response'});
  }

  saveToken(jwt) {
    localStorage.setItem("token",jwt);
    this.jwt=jwt;
    this.parseJWT(jwt);
  }

  private parseJWT(jwt) {
    let jwrHelper=new JwtHelperService();
    let jwtObjects=jwrHelper.decodeToken(this.jwt);
    this.username=jwtObjects.sub;
    let roles=jwtObjects.roles;
    this.roles=[];
    roles.forEach(r=>{
      this.roles.push(r['authority']);
    })
  }

  isAdmin(){
    return this.roles.indexOf("ADMIN")>=0;
  }

  isManager(){
    return this.roles.indexOf("ANALYSE_MANAGER")>=0;
  }

  isUser(){
    return this.roles.indexOf("USER")>=0;
  }

  isAuthenticated(){
    return this.roles!=undefined;
  }

  loadToken() {
    let jwt=undefined
    jwt=localStorage.getItem('token');
    if(jwt!=undefined){
      this.jwt=jwt;
      this.parseJWT(jwt);
    }
  }

  logOut() {
    localStorage.removeItem('token');
    this.initParams();
  }

  initParams(){
    this.jwt=undefined;
    this.username=undefined;
    this.roles=undefined;
  }
}
