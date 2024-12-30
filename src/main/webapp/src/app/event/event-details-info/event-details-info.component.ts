import {Component, Input} from '@angular/core';
import {EventInfo} from "@open-event-workspace/core";

@Component({
    selector: 'app-event-details-info',
    templateUrl: './event-details-info.component.html',
    styleUrl: './event-details-info.component.scss',
    standalone: false
})
export class EventDetailsInfoComponent {
  @Input() info: EventInfo | undefined
}
