import {Injectable} from '@angular/core';
import {BaseService} from "../../shared/model/base-service";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {EventSearchRequest, EventSearchResponse} from "./search-api";

@Injectable({
  providedIn: 'root'
})
export class SearchService extends BaseService {
  constructor(http: HttpClient) {
    super(http, 'search')
    this.retryCount = 0
  }

  setupEvents(): Observable<any> {
    return this.post('setup/event', {})
  }

  searchEvents(request: EventSearchRequest, page: number, size: number): Observable<EventSearchResponse> {
    let params = new HttpParams()
      .set("page", page)
      .set("size", size)
    return this.post('event', params)
  }
}
