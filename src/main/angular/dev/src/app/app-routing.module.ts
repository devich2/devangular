import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ChatcreateComponent} from "./chatcreate/chatcreate.component";
import {MessagesComponent} from "./messages/messages.component";
import {ChatComponent} from "./chat/chat.component";

const routes: Routes = [
   { path: '', redirectTo: '/createchat', pathMatch: 'full' },
  {path:'createchat',component: ChatcreateComponent},
  {path: 'chat/:name', component: ChatComponent}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
