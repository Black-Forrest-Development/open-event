import {Component, input} from '@angular/core';
import {EventInfo} from "@open-event-workspace/core";
import {BoardCardComponent} from "../../../shared/board-card/board-card.component";
import {RegistrationTableComponent} from "../../registration/registration-table/registration-table.component";

@Component({
  selector: 'app-event-details-registration',
  imports: [
    BoardCardComponent,
    RegistrationTableComponent
  ],
  templateUrl: './event-details-registration.component.html',
  styleUrl: './event-details-registration.component.scss'
})
export class EventDetailsRegistrationComponent {
  event = input.required<EventInfo>()
}
