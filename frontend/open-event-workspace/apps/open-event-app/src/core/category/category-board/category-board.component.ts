import {Component, EventEmitter} from '@angular/core';
import {debounceTime, distinctUntilChanged} from "rxjs";
import {MatDialog} from "@angular/material/dialog";
import {CategoryDeleteDialogComponent} from "../category-delete-dialog/category-delete-dialog.component";
import {CategoryChangeDialogComponent} from "../category-change-dialog/category-change-dialog.component";
import {Category, CategorySearchRequest, CategorySearchResponse, SearchService} from "@open-event-workspace/core";
import {MatToolbar} from "@angular/material/toolbar";
import {MatFormField} from "@angular/material/form-field";
import {MatIcon} from "@angular/material/icon";
import {TranslatePipe} from "@ngx-translate/core";
import {MatInput} from "@angular/material/input";
import {MatButton, MatIconAnchor, MatIconButton} from "@angular/material/button";
import {MatProgressBar} from "@angular/material/progress-bar";
import {MatCard} from "@angular/material/card";
import {MatCell, MatColumnDef, MatHeaderCell, MatHeaderRow, MatRow, MatTable} from "@angular/material/table";
import {MatDivider} from "@angular/material/divider";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-category-board',
  templateUrl: './category-board.component.html',
  styleUrls: ['./category-board.component.scss'],
  imports: [
    MatToolbar,
    MatFormField,
    MatIcon,
    TranslatePipe,
    MatInput,
    MatIconButton,
    MatButton,
    MatProgressBar,
    MatCard,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatIconAnchor,
    MatDivider,
    MatHeaderRow,
    MatPaginator,
    MatRow
  ],
  standalone: true
})
export class CategoryBoardComponent {

  reloading: boolean = false
  pageSize: number = 20
  pageIndex: number = 0
  totalSize: number = 0
  categories: Category[] = []

  keyUp: EventEmitter<string> = new EventEmitter<string>()
  request = new CategorySearchRequest('')
  displayedColumns: string[] = ['name', 'cmd']

  constructor(private searchService: SearchService, private dialog: MatDialog) {

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
    this.reload(0, this.pageSize)
  }

  private reload(page: number, size: number) {
    if (this.reloading) return
    this.reloading = true
    this.searchService.searchCategories(this.request, page, size).subscribe(
      {
        next: value => this.handleData(value),
        error: e => this.handleError(e)
      }
    )
  }


  private handleData(response: CategorySearchResponse) {
    let p = response.result
    this.categories = p.content
    this.pageSize = p.pageable.size
    this.pageIndex = p.pageable.number
    this.totalSize = p.totalSize
    this.reloading = false
  }

  private handleError(err: any) {
    // this.toast.error("Failed to load data")
    this.reloading = false
  }

  create() {
    const dialogRef = this.dialog.open(CategoryChangeDialogComponent, {
      width: '350px',
      data: null
    })
    dialogRef.afterClosed().subscribe(d => this.search())
  }

  edit(entry: Category) {
    const dialogRef = this.dialog.open(CategoryChangeDialogComponent, {
      width: '350px',
      data: entry
    })
    dialogRef.afterClosed().subscribe(d => this.search())
  }

  delete(entry: Category) {
    const dialogRef = this.dialog.open(CategoryDeleteDialogComponent, {
      width: '350px',
      data: entry
    })
    dialogRef.afterClosed().subscribe(d => this.search())
  }
}
