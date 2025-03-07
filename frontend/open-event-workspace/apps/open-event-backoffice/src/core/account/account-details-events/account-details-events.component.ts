import {Component, computed, input, resource, signal} from '@angular/core';
import {Account, AccountDisplayNamePipe, Address} from "@open-event-workspace/core";
import {MatDivider} from "@angular/material/divider";
import {TranslatePipe} from "@ngx-translate/core";
import {LoadingBarComponent, toPromise} from "@open-event-workspace/shared";
import {AccountService} from "@open-event-workspace/backoffice";
import {MatTableModule} from "@angular/material/table";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatPaginatorModule, PageEvent} from "@angular/material/paginator";
import {DatePipe} from "@angular/common";

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

  constructor(private service: AccountService) {
  }

  handlePageChange($event: PageEvent) {
    this.page.set($event.pageIndex)
    this.size.set($event.pageSize)
  }

  editEvent(address: Address) {

  }

  deleteEvent(address: Address) {

  }
}
