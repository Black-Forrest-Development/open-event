import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {SettingsRoutingModule} from './settings-routing.module';
import {SettingsBoardComponent} from './settings-board/settings-board.component';
import {SettingsChangeComponent} from './settings-change/settings-change.component';


@NgModule({
  declarations: [
    SettingsBoardComponent,
    SettingsChangeComponent
  ],
  imports: [
    CommonModule,
    SettingsRoutingModule
  ]
})
export class SettingsModule { }
