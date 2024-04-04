import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AddressBoardComponent} from './address-board/address-board.component';
import {MatCard} from "@angular/material/card";
import {PreferencesModule} from "../preferences/preferences.module";


@NgModule({
    declarations: [
        AddressBoardComponent
    ],
    exports: [
        AddressBoardComponent
    ],
  imports: [
    CommonModule,
    MatCard,
    PreferencesModule
  ]
})
export class AddressModule { }
