import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {EventSearchEntry, EventSearchRequest, EventSearchResponse} from "../../search/model/search-api";
import {SearchService} from "../../search/model/search.service";
import {PageEvent} from "@angular/material/paginator";

@Injectable({
  providedIn: 'root'
})
export class EventBoardService {

  reloading: BehaviorSubject<boolean> = new BehaviorSubject(false);
  searching: boolean = false
  pageSize: number = 20
  pageIndex: number = 0
  totalSize: number = 0
  hasMoreElements: boolean = false
  entries: EventSearchEntry[] = []
  request: EventSearchRequest = new EventSearchRequest('', undefined, undefined, false, false)
  infiniteScrollMode: boolean = false

  constructor(private searchService: SearchService) {
  }

  search(request: EventSearchRequest) {
    this.request = request
    if (this.searching) return
    this.pageIndex = 0
    this.reload(this.pageIndex, this.pageSize)
  }

  private handleData(response: EventSearchResponse) {
    let value = response.result
    if (this.infiniteScrollMode) {
      this.entries.push(...value.content)
    } else {
      this.entries = value.content
    }
    this.pageSize = value.pageable.size
    this.pageIndex = value.pageable.number
    this.totalSize = value.totalSize
    this.hasMoreElements = value.content.length != 0 && this.pageIndex != (value.totalPages - 1)
    this.reloading.next(false)
    this.searching = false
  }

  private handleError(err: any) {
    // this.toast.error("Failed to load data")
    this.reloading.next(false)
  }

  onScroll() {
    if (this.reloading) return
    if (!this.hasMoreElements) return
    this.reload(this.pageIndex + 1, this.pageSize)
  }

  handlePageChange(event: PageEvent) {
    this.reload(event.pageIndex, event.pageSize)
  }

  private reload(page: number, size: number) {
    if (this.reloading.value) return
    this.reloading.next(true)
    this.searchService.searchEvents(this.request, page, size).subscribe(
      {
        next: value => this.handleData(value),
        error: e => this.handleError(e)
      }
    )
  }
}
