import {Component, computed, input, output, signal} from '@angular/core';
import {MatDivider} from "@angular/material/divider";
import {MatIcon} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {TranslateModule} from "@ngx-translate/core";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {ActivatedRoute, Data, Params} from "@angular/router";

@Component({
  selector: 'app-event-action',
  imports: [MatDivider, MatIcon, MatButtonModule, MatProgressSpinner, TranslateModule],
  templateUrl: './event-action.component.html',
  styleUrl: './event-action.component.scss'
})
export class EventActionComponent {

  processing = input(false)
  status = input.required<string>()

  action = signal('')
  participationPossible = computed(() => this.status() !== 'UNCONFIRMED' && !this.processing() && this.action() === '')

  participantId = signal<string | undefined>(undefined)
  confirmationPossible = signal(() => this.action() === 'confirm' && this.participantId() && this.status() === 'UNCONFIRMED')

  visible = computed(() => this.participationPossible() || this.confirmationPossible())

  participateEvent = output()
  confirmEvent = output<string>()

  constructor(private route: ActivatedRoute) {
    this.route.data.subscribe(d => this.handleRouteData(d))
    this.route.queryParams.subscribe(p => this.handleQueryParams(p))
  }


  private handleRouteData(d: Data) {
    let action = d['action']
    if (action) this.action.set(action)
  }

  private handleQueryParams(p: Params) {
    let participantId = p['pid']
    this.participantId.set(participantId)
  }
}
