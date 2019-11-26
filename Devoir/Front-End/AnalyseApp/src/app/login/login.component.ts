import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../services/authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public style;
  public message;

  constructor(private authServive:AuthenticationService,private router:Router) { }

  ngOnInit() {
    this.style=undefined;
    this.message=undefined;
  }

  onLogin(data){
    this.authServive.login(data).subscribe(
      response=>{
        let jwt=response.headers.get("Authorization");
        this.authServive.saveToken(jwt);
        this.router.navigate(['']);
    },err=>{
        this.message="mauvais mot de passe ou username";
        this.style="alert-danger";
        console.log(err);
      }
    )
  }

}
