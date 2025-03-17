import {Component, computed, input, resource, signal} from '@angular/core';
import {Account, AccountDisplayNamePipe, Event} from "@open-event-workspace/core";
import {MatDivider} from "@angular/material/divider";
import {TranslatePipe} from "@ngx-translate/core";
import {LoadingBarComponent, toPromise} from "@open-event-workspace/shared";
import {AccountService} from "@open-event-workspace/backoffice";
import {MatTableModule} from "@angular/material/table";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatPaginatorModule, PageEvent} from "@angular/material/paginator";
import {DatePipe} from "@angular/common";
import {MatDialog} from "@angular/material/dialog";
import {EventCreateDialogComponent} from "../../event/event-create-dialog/event-create-dialog.component";
import {EventChangeDialogComponent} from "../../event/event-change-dialog/event-change-dialog.component";
import {EventDeleteDialogComponent} from "../../event/event-delete-dialog/event-delete-dialog.component";
import {EventPublishDialogComponent} from "../../event/event-publish-dialog/event-publish-dialog.component";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-account-details-events',
  imports: [
    MatDivider,
    TranslatePipe,
    LoadingBarComponent,
    MatDivider,
    MatTableModule,
    MatPaginatorModule,
    MatIconModule,
    MatButtonModule,
    AccountDisplayNamePipe,
    DatePipe,
    RouterLink,
  ],
  templateUrl: './account-details-events.component.html',
  styleUrl: './account-details-events.component.scss'
})
export class AccountDetailsEventsComponent {
  data = input.required<Account>()

  page = signal(0)
  size = signal(20)

  eventsCriteria = computed(() => ({
    data: this.data(),
    page: this.page(),
    size: this.size(),
  }));

  eventsResource = resource({
    request: this.eventsCriteria,
    loader: (param) => {
      return toPromise(this.service.getEvents(param.request.data.id, param.request.page, param.request.size))
    }
  })

  result = computed(this.eventsResource.value ?? undefined)

  events = computed(() => this.result()?.content ?? [])
  totalSize = computed(() => this.result()?.totalSize ?? 0)
  loading = this.eventsResource.isLoading
  error = this.eventsResource.error

  displayedColumns: string[] = ['id', 'owner', 'title', 'start', 'finish', 'published', 'tags', 'cmd']

  constructor(private service: AccountService, private dialog: MatDialog) {
  }

  handlePageChange($event: PageEvent) {
    this.page.set($event.pageIndex)
    this.size.set($event.pageSize)
  }

  createEvent() {
    this.dialog.open(EventCreateDialogComponent, {data: this.data()}).afterClosed().subscribe(value => {
      if (value) this.eventsResource.reload()
    })
  }

  editEvent(event: Event) {
    this.dialog.open(EventChangeDialogComponent, {data: event}).afterClosed().subscribe(value => {
      if (value) this.eventsResource.reload()
    })
  }

  deleteEvent(event: Event) {
    this.dialog.open(EventDeleteDialogComponent, {data: event}).afterClosed().subscribe(value => {
      if (value) this.eventsResource.reload()
    })
  }

  publishEvent(event: Event) {
    this.dialog.open(EventPublishDialogComponent, {data: event}).afterClosed().subscribe(value => {
      if (value) this.eventsResource.reload()
    })
  }
}
