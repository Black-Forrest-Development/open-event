import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {BaseService} from "@open-event-workspace/shared";
import {EventSearchRequest, EventSearchResponse} from "@open-event-workspace/core";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class EventService extends BaseService {

  constructor(http: HttpClient) {
    super(http, 'app/event')
    this.retryCount = 1
  }

  search(request: EventSearchRequest, page: number, size: number): Observable<EventSearchResponse> {
    let params = new HttpParams()
      .set("page", page)
      .set("size", size)
    return this.post('search', request, params)
  }

}
