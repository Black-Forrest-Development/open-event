import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ShareRoutingModule} from './share-routing.module';
import {ShareDetailsComponent} from './share-details/share-details.component';
import {AccountModule} from "../account/account.module";
import {MatCard, MatCardActions, MatCardContent, MatCardHeader, MatCardImage} from "@angular/material/card";
import {MatChip, MatChipListbox} from "@angular/material/chips";
import {MatDivider} from "@angular/material/divider";
import {ShareButtonsModule} from "ngx-sharebuttons/buttons";
import {RegistrationModule} from "../registration/registration.module";
import {TranslateModule} from "@ngx-translate/core";
import {MatSlideToggle} from "@angular/material/slide-toggle";
import {MatProgressBar} from "@angular/material/progress-bar";


@NgModule({
  declarations: [
    ShareDetailsComponent
  ],
  exports: [
    ShareDetailsComponent
  ],
  imports: [
    CommonModule,
    ShareRoutingModule,
    AccountModule,
    MatCard,
    MatCardActions,
    MatCardContent,
    MatCardHeader,
    MatCardImage,
    MatChip,
    MatChipListbox,
    MatDivider,
    ShareButtonsModule,
    RegistrationModule,
    TranslateModule,
    MatSlideToggle,
    MatProgressBar
  ]
})
export class ShareModule { }
