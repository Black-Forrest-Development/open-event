import {Component} from '@angular/core';
import {EventBoardService} from "../event-board.service";
import {MatProgressBar} from "@angular/material/progress-bar";
import {AsyncPipe, DatePipe} from "@angular/common";
import {MatCard} from "@angular/material/card";
import {MatCell, MatColumnDef, MatHeaderCell, MatHeaderRow, MatRow, MatTable} from "@angular/material/table";
import {MatIcon} from "@angular/material/icon";
import {TranslatePipe} from "@ngx-translate/core";
import {AccountComponent} from "../../account/account/account.component";
import {RegistrationStatusComponent} from "../../registration/registration-status/registration-status.component";
import {MatDivider} from "@angular/material/divider";
import {MatMiniFabButton} from "@angular/material/button";
import {RouterLink} from "@angular/router";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-event-board-table',
  templateUrl: './event-board-table.component.html',
  styleUrls: ['./event-board-table.component.scss'],
  imports: [
    MatProgressBar,
    AsyncPipe,
    MatCard,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatIcon,
    TranslatePipe,
    AccountComponent,
    MatCell,
    DatePipe,
    RegistrationStatusComponent,
    MatDivider,
    MatMiniFabButton,
    MatRow,
    MatHeaderRow,
    RouterLink,
    MatPaginator
  ],
  standalone: true
})
export class EventBoardTableComponent {

  // displayedColumns: string[] = ['account', 'period', 'location', 'description', 'status', 'category', 'publish']
  displayedColumns: string[] = ['account', 'period', 'location', 'description', 'status', 'label']

  constructor(public service: EventBoardService) {
  }
}
