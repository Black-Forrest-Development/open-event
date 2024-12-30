import {Component} from '@angular/core';
import {EventBoardService} from "../event-board.service";
import {EventBoardFilterComponent} from "../event-board-filter/event-board-filter.component";
import {MatProgressBar} from "@angular/material/progress-bar";
import {AsyncPipe} from "@angular/common";
import {EventBoardListEntryComponent} from "../event-board-list-entry/event-board-list-entry.component";
import {MatButton, MatMiniFabButton} from "@angular/material/button";
import {TranslatePipe} from "@ngx-translate/core";
import {ScrollNearEndDirective} from "../../../shared/scroll-near-end.directive";

@Component({
  selector: 'app-event-board-list',
  templateUrl: './event-board-list.component.html',
  styleUrls: ['./event-board-list.component.scss'],
  imports: [
    EventBoardFilterComponent,
    MatProgressBar,
    AsyncPipe,
    EventBoardListEntryComponent,
    MatButton,
    TranslatePipe,
    MatMiniFabButton,
    ScrollNearEndDirective,
  ],
  standalone: true
})
export class EventBoardListComponent {

  filterOverlayOpen: boolean = false

  constructor(public service: EventBoardService) {
    this.filterOverlayOpen = service.filterToolbarVisible
  }

}
