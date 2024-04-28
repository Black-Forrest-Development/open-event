import {Injectable} from '@angular/core';
import {EventService} from "./event.service";
import {HotToastService} from "@ngxpert/hot-toast";
import {BehaviorSubject} from "rxjs";
import {PageEvent} from "@angular/material/paginator";
import {EventSearchEntry, EventSearchRequest, EventSearchResponse} from "../../search/model/search-api";
import {SearchService} from "../../search/model/search.service";

@Injectable({
  providedIn: 'root'
})
export class EventBoardService {

  reloading: BehaviorSubject<boolean> = new BehaviorSubject(false);
  searching: boolean = false
  pageSize: number = 20
  pageIndex: number = 0
  totalSize: number = 0
  entries: EventSearchEntry[] = []
  query: string = ''

  constructor(private service: EventService, private searchService: SearchService, private toast: HotToastService) {
  }

  reload(page: number, size: number) {
    if (this.reloading.value) return
    this.reloading.next(true)
    let request = new EventSearchRequest(this.query, undefined, undefined, false, false)
    this.searchService.searchEvents(request, page, size).subscribe(
      {
        next: value => this.handleData(value),
        error: e => this.handleError(e)
      }
    )
  }

  search(query: string) {
    this.query = query
    if (this.searching) return
    this.reload(this.pageIndex, this.pageSize)
  }

  handlePageChange(event: PageEvent) {
    this.reload(event.pageIndex, event.pageSize)
  }

  private handleData(response: EventSearchResponse) {
    let value = response.result
    this.entries = value.content
    this.pageSize = value.pageable.size
    this.pageIndex = value.pageable.number
    this.totalSize = value.totalSize
    this.reloading.next(false)
    this.searching = false
  }

  private handleError(err: any) {
    // this.toast.error("Failed to load data")
    this.reloading.next(false)
  }
}
