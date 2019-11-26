import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ProductsComponent} from './products/products.component';
import {CategoriesComponent} from './categories/categories.component';
import {LoginComponent} from './login/login.component';
import {AdminCategoriesComponent} from './admin-categories/admin-categories.component';
import {AdminProductsComponent} from './admin-products/admin-products.component';
import {AdminUsersComponent} from './admin-users/admin-users.component';
import {HomeComponent} from './home/home.component';
import {ClientDetailsComponent} from "./client-details/client-details.component";

const routes: Routes = [
  {path:"products/:url",component:ProductsComponent},
  {path:"home",component:HomeComponent},
  {path:"categories",component:CategoriesComponent},
  {path:"login",component:LoginComponent},
  {path:"adminCategories",component:AdminCategoriesComponent},
  {path:"adminProducts",component:AdminProductsComponent},
  {path:"adminUsers",component:AdminUsersComponent},
  {path:"client-details/:url",component:ClientDetailsComponent},
  {path:"",redirectTo:"home",pathMatch:"full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
