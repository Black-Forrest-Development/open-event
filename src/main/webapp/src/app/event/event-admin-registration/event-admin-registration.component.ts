import {Component, Input, ViewChild} from '@angular/core';
import {EventInfo} from "../model/event-api";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {Participant} from "../../participant/model/participant-api";

@Component({
  selector: 'app-event-admin-registration',
  templateUrl: './event-admin-registration.component.html',
  styleUrl: './event-admin-registration.component.scss'
})
export class EventAdminRegistrationComponent {
  @Input()
  set info(value: EventInfo | undefined) {
    this.data = value
    if (value) {
      this.dataSource.data = value.registration?.participants ?? []
    } else {
      this.dataSource.data = []
    }
  }

  data: EventInfo | undefined
  displayedColumns: string[] = ['rank', 'size', 'status', 'waitinglist', 'name', 'email', 'phone', 'mobile', 'timestamp']
  dataSource = new MatTableDataSource<Participant>([])

  constructor() {
  }

  @ViewChild(MatSort) sort: MatSort | undefined;

  ngAfterViewInit() {
    if (this.sort) this.dataSource.sort = this.sort
  }


}
