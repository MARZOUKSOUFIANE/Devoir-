import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from './services/authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'catalogueWebAoo';

  constructor(private authService:AuthenticationService,private router:Router){}

  ngOnInit(): void {
    this.authService.loadToken();
  }

  isAdmin(){
    return this.authService.isAdmin();
  }

  isManager(){
    return this.authService.isManager();
  }

  isUser(){
    return this.authService.isUser();
  }

  isAuthenticated(){
    return this.authService.isAuthenticated();
  }


  onLogOut() {
    this.authService.logOut();
    this.router.navigate(['login']);
  }
}
