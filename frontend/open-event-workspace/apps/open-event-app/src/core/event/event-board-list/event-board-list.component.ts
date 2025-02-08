import {Component} from '@angular/core';
import {EventBoardService} from "../event-board.service";
import {EventBoardFilterComponent} from "../event-board-filter/event-board-filter.component";
import {EventBoardListEntryComponent} from "../event-board-list-entry/event-board-list-entry.component";
import {MatButton, MatMiniFabButton} from "@angular/material/button";
import {TranslatePipe} from "@ngx-translate/core";
import {ScrollNearEndDirective} from "../../../shared/scroll-near-end.directive";
import {MatIcon} from "@angular/material/icon";
import {LoadingBarComponent} from "../../../shared/loading-bar/loading-bar.component";

@Component({
  selector: 'app-event-board-list',
  templateUrl: './event-board-list.component.html',
  styleUrls: ['./event-board-list.component.scss'],
  imports: [
    EventBoardFilterComponent,
    EventBoardListEntryComponent,
    MatButton,
    TranslatePipe,
    MatMiniFabButton,
    ScrollNearEndDirective,
    MatIcon,
    LoadingBarComponent,
  ],
  standalone: true
})
export class EventBoardListComponent {

  filterOverlayOpen: boolean = false

  constructor(public service: EventBoardService) {
    this.filterOverlayOpen = service.filterToolbarVisible
  }

}
