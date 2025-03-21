import {Component, EventEmitter} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EventSearchEntry, EventSearchRequest, EventSearchResponse} from "@open-event-workspace/core";
import {EventService} from "@open-event-workspace/backoffice";
import {HotToastService} from "@ngxpert/hot-toast";
import {MatDialog} from "@angular/material/dialog";
import {debounceTime, distinctUntilChanged} from "rxjs";
import {PageEvent} from "@angular/material/paginator";
import {EventChangeDialogComponent} from "./event-change-dialog/event-change-dialog.component";
import {EventDeleteDialogComponent} from "./event-delete-dialog/event-delete-dialog.component";
import {LoadingBarComponent} from "@open-event-workspace/shared";
import {MatIconButton, MatMiniFabButton} from "@angular/material/button";
import {MatCard} from "@angular/material/card";
import {MatFormField, MatLabel, MatSuffix} from "@angular/material/form-field";
import {MatIcon} from "@angular/material/icon";
import {MatInput} from "@angular/material/input";
import {MatToolbar} from "@angular/material/toolbar";
import {TranslatePipe} from "@ngx-translate/core";
import {EventTableComponent} from "./event-table/event-table.component";

@Component({
  selector: 'boffice-event',
  imports: [CommonModule, LoadingBarComponent, MatCard, MatFormField, MatIcon, MatIconButton, MatInput, MatLabel, MatMiniFabButton, MatSuffix, MatToolbar, TranslatePipe, EventTableComponent],
  templateUrl: './event.component.html',
  styleUrl: './event.component.scss',
})
export class EventComponent {


  reloading: boolean = false
  pageSize: number = 20
  pageNumber: number = 0
  totalElements: number = 0
  data: EventSearchEntry[] = []

  keyUp: EventEmitter<string> = new EventEmitter<string>()
  request = new EventSearchRequest('', undefined, undefined, false, false, false)

  constructor(private service: EventService, private toast: HotToastService, private dialog: MatDialog) {
  }


  ngOnInit() {
    this.search()
    this.keyUp.pipe(
      debounceTime(500),
      distinctUntilChanged()
    ).subscribe(query => this.fullTextSearch = query)
  }


  set fullTextSearch(val: string) {
    if (this.request.fullTextSearch === val) return
    this.request.fullTextSearch = val
    this.search()
  }

  get fullTextSearch(): string {
    return this.request.fullTextSearch
  }

  search() {
    this.load(0, this.pageSize)
  }


  reload() {
    this.load(0, this.pageSize)
  }

  private load(page: number, size: number) {
    if (this.reloading) return
    this.reloading = true
    this.service.search(this.request, page, size).subscribe(
      {
        next: value => this.handleData(value),
        error: e => this.handleError(e)
      }
    )
  }


  private handleData(response: EventSearchResponse) {
    let p = response.result
    this.data = p.content
    this.pageSize = p.pageable.size
    this.pageNumber = p.pageable.number
    this.totalElements = p.totalSize
    this.reloading = false
  }

  private handleError(err: any) {
    if (err) this.toast.error(err)
    this.reloading = false
  }

  handlePageChange(event: PageEvent) {
    if (this.reloading) return
    this.pageSize = event.pageSize
    this.load(event.pageIndex, event.pageSize)
  }


  edit(entry: EventSearchEntry) {
    const dialogRef = this.dialog.open(EventChangeDialogComponent, {
      width: '350px',
      data: entry
    })
    dialogRef.afterClosed().subscribe(d => this.search())
  }

  delete(entry: EventSearchEntry) {
    const dialogRef = this.dialog.open(EventDeleteDialogComponent, {
      width: '350px',
      data: entry
    })
    dialogRef.afterClosed().subscribe(d => this.search())
  }

}
