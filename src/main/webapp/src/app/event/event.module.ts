import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {EventRoutingModule} from './event-routing.module';
import {MaterialModule} from "../material/material.module";
import {EventBoardComponent} from './event-board/event-board.component';
import {EventBoardHeaderComponent} from './event-board-header/event-board-header.component';
import {TranslateModule} from "@ngx-translate/core";


@NgModule({
  declarations: [
    EventBoardComponent,
    EventBoardHeaderComponent
  ],
  imports: [
    CommonModule,
    EventRoutingModule,
    MaterialModule,
    TranslateModule,
  ]
})
export class EventModule {
}
