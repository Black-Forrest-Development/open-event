import {Injectable} from '@angular/core';
import {BaseService} from "@open-event-workspace/shared";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {PublicEvent} from "./event.api";

@Injectable({
  providedIn: 'root'
})
export class EventService extends BaseService {

  constructor(http: HttpClient) {
    super(http, 'external/event')
    this.retryCount = 0
  }

  getEvent(id: string): Observable<PublicEvent> {
    return this.get(id)
  }

}
