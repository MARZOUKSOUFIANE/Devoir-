import { Component, OnInit } from '@angular/core';
import {UserService} from '../services/user.service';


@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css']
})
export class AdminUsersComponent implements OnInit {

  public users;
  public mode:string='list-users';
  public currentUser;
  private style:string=undefined;
  private message=undefined;

  constructor(private userService:UserService) { }

  ngOnInit() {
    this.getAllUsers()
    this.style=undefined;
    this.message=undefined;
  }

  getAllUsers(){
    this.userService.getAllUsers().
    subscribe(data=>{
      this.users=data;
    },err=>{
      console.log(err)
    })
  }

  onDeleteUser(user) {
    let c = confirm("Etes vous sure ?")
    if (c) {
      this.userService.deleteUser(user._links.self.href).subscribe(
        data => {
          this.getAllUsers();
        }, err => {
          console.log(err)
        }
      )
    }
  }

  onNewUser() {
    this.mode='new-user'
  }

  onSaveUser(value) {
    this.userService.saveUser(value).
    subscribe(data=>{
      this.getAllUsers();
      this.mode='list-users';
      this.style='alert-success';
      this.message='l\'utilisateur est ajoute avec succes ';
      this.mode='list-users'
    },err=>{
        this.style='alert-danger';
        this.message=err.error.message;
    })
  }

  onEditUser(user) {
    this.mode='edit-user';
    this.userService.getRessource(user._links.self.href).
    subscribe(data=>{
      this.currentUser=data;
    },err=>{
      console.log(err);
    })
  }

  onSaveUserEdit(value) {
    this.userService.saveUserEdit(value,this.currentUser._links.self.href).
    subscribe(data=>{
      this.getAllUsers();
      this.mode='list-users';
      this.style='alert-success';
      this.message='l\'utilisateur est modifie avec succes ';
    },err=>{
      this.style='alert-danger';
      if(err.error.message.toString().includes('could not execute statement')){
        this.message='Ce utilisateur exist deja.'
      }
    })
  }

  onSaveRoleUserEdit(value) {
    this.userService.addUserRole(value).
      subscribe(data=>{
        this.mode="list-users";
        console.log(value)
        this.currentUser=data;
        this.getAllUsers();
    },err=>{
        console.log(err);
    })

  }

  onEditRoles(user) {
    this.mode='edit-roles';
    this.userService.getRessource(user._links.self.href).
    subscribe(data=>{
      this.currentUser=data;
    },err=>{
      console.log(err);
    })
  }

  onEditUserPassword(user) {
    this.mode="edit-user-pass";
    this.currentUser=user;
  }

  onSaveUserEditPass(value) {
    this.userService.changeUserPassword(value).subscribe(
      data=>{

      },err=>{
        if(err.error.message=="Password changed successfuly"){
          this.style='alert-success';
          this.message=err.error.message;
          this.mode="list-users";
        }
        else if(err.error.message=="confirmation password failed"){
          this.style='alert-danger';
          this.message=err.error.message;
        }
      }
    )
  }
}
