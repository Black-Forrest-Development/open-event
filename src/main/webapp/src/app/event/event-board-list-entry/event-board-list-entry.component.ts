import {Component, Input} from '@angular/core';
import {Event} from "../model/event-api";

@Component({
  selector: 'app-event-board-list-entry',
  templateUrl: './event-board-list-entry.component.html',
  styleUrls: ['./event-board-list-entry.component.scss']
})
export class EventBoardListEntryComponent {
  @Input() event: Event | undefined
}
