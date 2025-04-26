import {Component, input, output} from '@angular/core';
import {Issue} from "@open-event-workspace/core";
import {MatPaginatorModule, PageEvent} from "@angular/material/paginator";
import {DatePipe} from "@angular/common";
import {MatTableModule} from "@angular/material/table";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {RouterLink} from "@angular/router";
import {TranslatePipe} from "@ngx-translate/core";

@Component({
  selector: 'app-issue-table',
  imports: [
    DatePipe,
    MatTableModule,
    MatPaginatorModule,
    MatIconModule,
    MatButtonModule,
    RouterLink,
    TranslatePipe
  ],
  templateUrl: './issue-table.component.html',
  styleUrl: './issue-table.component.scss'
})
export class IssueTableComponent {
  data = input.required<Issue[]>()
  reloading = input.required<boolean>()
  pageNumber = input.required<number>()
  pageSize = input.required<number>()
  totalElements = input.required<number>()

  pageEvent = output<PageEvent>()
  displayedColumns: string[] = ['error', 'actor', 'status', 'timestamp', 'cmd']
}
