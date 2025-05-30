import {Component, computed, input, output} from "@angular/core";
import {PublicEvent} from "@open-event-workspace/external";
import {MatCard} from "@angular/material/card";
import {DatePipe} from "@angular/common";
import {MatDivider} from "@angular/material/divider";
import {TranslatePipe} from "@ngx-translate/core";
import {RegistrationStatusComponent} from "@open-event-workspace/core";
import {MatButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatProgressSpinner} from "@angular/material/progress-spinner";

@Component({
  selector: 'app-event-info',
  imports: [
    MatCard,
    DatePipe,
    MatDivider,
    TranslatePipe,
    RegistrationStatusComponent,
    MatButton,
    MatIcon,
    MatProgressSpinner
  ],
  templateUrl: './event-info.component.html',
  styleUrl: './event-info.component.scss'
})
export class EventInfoComponent {

  event = input.required<PublicEvent>()
  status = input.required<string>()
  processing = input(false)
  participationPossible = computed(() => this.possibleStatus(this.status()) && !this.processing())
  participateEvent = output()


  private possibleStatus(status: string): boolean {
    if (!status) return true
    return status !== 'UNCONFIRMED'
  }

}
