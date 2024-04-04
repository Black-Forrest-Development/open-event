import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ProfileRoutingModule} from './profile-routing.module';
import {ProfileBoardComponent} from './profile-board/profile-board.component';
import {MaterialModule} from "../material/material.module";
import {TranslateModule} from "@ngx-translate/core";
import {ReactiveFormsModule} from "@angular/forms";
import {AddressModule} from "../address/address.module";
import {PreferencesModule} from "../preferences/preferences.module";
import {ProfileChangeComponent} from './profile-change/profile-change.component';


@NgModule({
  declarations: [
    ProfileBoardComponent,
    ProfileChangeComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    TranslateModule,
    ReactiveFormsModule,
    ProfileRoutingModule,
    AddressModule,
    PreferencesModule,
  ]
})
export class ProfileModule {
}
