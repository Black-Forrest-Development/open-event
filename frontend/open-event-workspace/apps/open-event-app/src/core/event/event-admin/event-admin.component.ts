import {Component} from '@angular/core';
import {ActivatedRoute, ParamMap} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {Location, NgIf} from "@angular/common";
import {EventInfo, EventService} from "@open-event-workspace/core";
import {MatToolbar} from "@angular/material/toolbar";
import {MatIcon} from "@angular/material/icon";
import {EventActionExportComponent} from "../event-action-export/event-action-export.component";
import {MatProgressBar} from "@angular/material/progress-bar";
import {
  RegistrationModerationComponent
} from "../../registration/registration-moderation/registration-moderation.component";
import {MatMiniFabButton} from "@angular/material/button";

@Component({
  selector: 'app-event-admin',
  templateUrl: './event-admin.component.html',
  styleUrl: './event-admin.component.scss',
  imports: [
    MatToolbar,
    MatIcon,
    EventActionExportComponent,
    MatProgressBar,
    RegistrationModerationComponent,
    MatMiniFabButton,
    NgIf
  ],
  standalone: true
})
export class EventAdminComponent {

  reloading: boolean = false
  info: EventInfo | undefined
  eventId: number | undefined

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private service: EventService,
    public dialog: MatDialog,
  ) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe(p => this.handleParams(p))
  }


  reload() {
    if (!this.eventId) return
    if (this.reloading) return
    this.reloading = true
    this.service.getEventInfo(this.eventId).subscribe(d => this.handleData(d))
  }

  private handleParams(p: ParamMap) {
    let idParam = p.get('id')
    this.eventId = idParam !== null ? +idParam : undefined
    this.reload()
  }

  private handleData(d: EventInfo) {
    this.info = d
    this.reloading = false
  }

  back() {
    this.location.back()
  }
}
