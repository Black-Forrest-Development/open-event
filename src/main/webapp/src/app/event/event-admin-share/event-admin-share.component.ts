import {Component, Input} from '@angular/core';
import {EventInfo} from "../model/event-api";

@Component({
  selector: 'app-event-admin-share',
  templateUrl: './event-admin-share.component.html',
  styleUrl: './event-admin-share.component.scss'
})
export class EventAdminShareComponent {
  @Input() info: EventInfo | undefined
}
