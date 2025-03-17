import {Component, computed, resource, signal} from '@angular/core';
import {LoadingBarComponent, toPromise} from "@open-event-workspace/shared";
import {MatIcon} from "@angular/material/icon";
import {MatMiniFabButton} from "@angular/material/button";
import {MatToolbar} from "@angular/material/toolbar";
import {TranslatePipe} from "@ngx-translate/core";
import {EventService} from "@open-event-workspace/backoffice";
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";

@Component({
  selector: 'app-event-details',
  imports: [
    LoadingBarComponent,
    MatIcon,
    MatMiniFabButton,
    MatToolbar,
    TranslatePipe
  ],
  templateUrl: './event-details.component.html',
  styleUrl: './event-details.component.scss'
})
export class EventDetailsComponent {
  id = signal(-1)

  eventResource = resource({
    request: this.id,
    loader: (param) => {
      return toPromise(this.service.getEvent(param.request))
    }
  })


  event = computed(this.eventResource.value ?? undefined)
  loading = this.eventResource.isLoading
  error = this.eventResource.error

  constructor(private service: EventService, private route: ActivatedRoute, private location: Location) {

    this.route.paramMap.subscribe(params => {
      let id = params.get('id')!
      this.id.set(+id)
    })

  }

  back() {
    this.location.back()
  }
}
