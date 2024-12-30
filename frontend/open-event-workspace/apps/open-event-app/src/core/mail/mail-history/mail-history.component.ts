import {Component, EventEmitter} from '@angular/core';
import {HotToastService} from "@ngxpert/hot-toast";
import {debounceTime, distinctUntilChanged} from "rxjs";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {ActivatedRoute, Router} from "@angular/router";
import {DatePipe, Location, NgIf} from "@angular/common";
import {MailJobHistoryEntry, MailService, Page} from '@open-event-workspace/core';
import {MatToolbar} from "@angular/material/toolbar";
import {MatMiniFabButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {TranslatePipe} from "@ngx-translate/core";
import {MatProgressBar} from "@angular/material/progress-bar";
import {MatCard} from "@angular/material/card";
import {MatCell, MatColumnDef, MatHeaderCell, MatHeaderRow, MatRow, MatTable} from "@angular/material/table";

@Component({
  selector: 'app-mail-history',
  templateUrl: './mail-history.component.html',
  styleUrls: ['./mail-history.component.scss'],
  imports: [
    MatToolbar,
    MatMiniFabButton,
    NgIf,
    MatIcon,
    TranslatePipe,
    MatProgressBar,
    MatCard,
    MatTable,
    MatHeaderCell,
    DatePipe,
    MatCell,
    MatColumnDef,
    MatHeaderRow,
    MatRow,
    MatPaginator
  ],
  standalone: true
})
export class MailHistoryComponent {
  reloading: boolean = false
  pageNumber = 0
  pageSize = 25
  totalElements = 0

  data: MailJobHistoryEntry[] = []

  displayedColumns: string[] = ['timestamp', 'message']

  keyUp: EventEmitter<string> = new EventEmitter<string>()

  jobId: number | undefined

  constructor(
    private service: MailService,
    private toastService: HotToastService,
    private router: Router,
    private route: ActivatedRoute,
    private location: Location
  ) {
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')
    if (id != null) {
      this.jobId = +id
      this.loadPage(0)
    }

    this.keyUp.pipe(
      debounceTime(500),
      distinctUntilChanged()
    ).subscribe(data => this.search(data))
  }

  handlePageChange(event: PageEvent) {
    if (this.reloading) return
    this.pageSize = event.pageSize
    this.loadPage(event.pageIndex)
  }

  back() {
    this.location.back()
  }

  private loadPage(number: number) {
    if (!this.jobId) return
    if (this.reloading) return
    this.reloading = true

    this.service.getJobHistory(this.jobId, number, this.pageSize).subscribe(p => this.handleData(p))
  }

  private handleData(page: Page<MailJobHistoryEntry>) {
    if (page == null) {
      this.data = [];
      this.pageNumber = 0;
      this.pageSize = 20;
      this.totalElements = 0;
    } else {
      this.data = page.content.filter(d => d != null);
      this.pageNumber = page.pageable.number;
      this.pageSize = page.pageable.size;
      this.totalElements = page.totalSize;
    }
    this.reloading = false;
  }

  private search(data: string) {
    this.toastService.error("Sorry searching '" + data + "' is not supported yet")
  }
}
