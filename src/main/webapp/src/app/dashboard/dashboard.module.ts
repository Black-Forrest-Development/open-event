import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardComponent} from './dashboard/dashboard.component';
import {MaterialModule} from "../material/material.module";
import {AppRoutingModule} from "../app-routing.module";
import {LoadingScreenComponent} from './loading-screen/loading-screen.component';
import {TranslateModule} from "@ngx-translate/core";


@NgModule({
  declarations: [
    DashboardComponent,
    LoadingScreenComponent
  ],
  exports: [
    DashboardComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    AppRoutingModule,
    TranslateModule
  ]
})
export class DashboardModule {
}
