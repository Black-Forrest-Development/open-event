import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ActivityRoutingModule} from './activity-routing.module';
import {ActivityButtonComponent} from './activity-button/activity-button.component';
import {MaterialModule} from "../material/material.module";
import {TranslateModule} from "@ngx-translate/core";


@NgModule({
  declarations: [
    ActivityButtonComponent
  ],
  exports: [
    ActivityButtonComponent
  ],
  imports: [
    CommonModule,
    ActivityRoutingModule,
    MaterialModule,
    TranslateModule
  ]
})
export class ActivityModule {
}
