import {Component, Input} from '@angular/core';
import {EventInfo} from "@open-event-workspace/core";

@Component({
    selector: 'app-event-details-location',
    templateUrl: './event-details-location.component.html',
    styleUrl: './event-details-location.component.scss',
    standalone: false
})
export class EventDetailsLocationComponent {
  @Input() info: EventInfo | undefined
}
