import {Component} from '@angular/core';
import {EventService} from "../model/event.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {Event} from "../model/event-api";
import {Location} from "@angular/common";
import {HotToastService} from "@ngneat/hot-toast";

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.scss']
})
export class EventDetailsComponent {

  reloading: boolean = false
  publishing: boolean = false
  event: Event | undefined

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private location: Location,
    private service: EventService,
    private toastService: HotToastService,
  ) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe(p => this.handleParams(p))
  }

  private handleParams(p: ParamMap) {
    let idParam = p.get('id')
    let id = idParam !== null ? +idParam : null
    if (!id) return

    this.reloading = true;
    this.service.getEvent(id).subscribe(d => this.handleData(d))

  }

  private handleData(d: Event) {
    this.event = d
    this.reloading = false
    this.publishing = false
  }

  back() {
    this.location.back()
  }

  publish() {
    if (!this.event) return
    if (this.publishing || this.reloading) return
    this.publishing = true
    this.service.publish(this.event.id).subscribe({
      next: d => this.handleData(d),
      error: err => {
        this.toastService.error(err)
        this.publishing = false
      }
    })
  }
}
