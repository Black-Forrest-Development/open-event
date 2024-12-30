import {Component, EventEmitter} from '@angular/core';
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {FormControl, FormGroup} from '@angular/forms';
import {AccountDisplayNamePipe, HistoryEventInfo, HistoryService, Page} from "@open-event-workspace/core";
import {MatToolbar} from "@angular/material/toolbar";
import {TranslatePipe} from "@ngx-translate/core";
import {MatMiniFabButton} from "@angular/material/button";
import {MatProgressBar} from "@angular/material/progress-bar";
import {MatCard} from "@angular/material/card";
import {DatePipe, NgClass, NgIf} from "@angular/common";
import {MatDivider} from "@angular/material/divider";
import {MatCell, MatColumnDef, MatHeaderCell, MatHeaderRow, MatRow, MatTable} from "@angular/material/table";
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'app-history-board',
  templateUrl: './history-board.component.html',
  styleUrls: ['./history-board.component.scss'],
  imports: [
    MatToolbar,
    TranslatePipe,
    MatMiniFabButton,
    MatProgressBar,
    MatCard,
    NgClass,
    MatDivider,
    MatPaginator,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    DatePipe,
    AccountDisplayNamePipe,
    MatHeaderRow,
    MatRow,
    MatIcon,
    NgIf
  ],
  standalone: true
})
export class HistoryBoardComponent {
  reloading: boolean = false
  pageNumber = 0
  pageSize = 25
  totalElements = 0

  displayedColumns: string[] = ['timestamp', 'actor', 'type', 'message', 'source', 'info']

  keyUp: EventEmitter<string> = new EventEmitter<string>()

  data: HistoryEventInfo[] = []
  selected: HistoryEventInfo | undefined

  range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });

  constructor(
    private service: HistoryService
  ) {
  }

  ngOnInit() {
    this.reload()
  }


  reload() {
    this.loadPage(this.pageNumber)
  }

  handlePageChange(event: PageEvent) {
    if (this.reloading) return
    this.pageSize = event.pageSize
    this.loadPage(event.pageIndex)
  }


  private loadPage(number: number) {
    if (this.reloading) return
    this.reloading = true

    this.service.getAllHistoryEventInfos(number, this.pageSize).subscribe(p => this.handleData(p))
  }

  private handleData(page: Page<HistoryEventInfo>) {
    if (page == null) {
      this.data = []
      this.pageNumber = 0
      this.pageSize = 20
      this.totalElements = 0
      this.selected = undefined
    } else {
      this.data = page.content.filter(d => d != null)
      this.pageNumber = page.pageable.number
      this.pageSize = page.pageable.size
      this.totalElements = page.totalSize
      this.selected = this.data[0]
    }
    this.reloading = false
  }
}
