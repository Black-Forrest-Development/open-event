import {Injectable} from '@angular/core';
import {BaseService} from "../../base-service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../../page";
import {Event, EventChangeRequest, PatchRequest} from "./event-api";

@Injectable({
  providedIn: 'root'
})
export class EventService extends BaseService {
  constructor(http: HttpClient) {
    super(http, 'event')
    this.retryCount = 1
  }

  getAllEvents(page: number, size: number): Observable<Page<Event>> {
    return this.getPaged('', page, size)
  }

  getEvent(id: number): Observable<Event> {
    return this.get('' + id)
  }

  createEvent(request: EventChangeRequest): Observable<Event> {
    return this.post('', request)
  }

  publish(id: number): Observable<Event> {
    return this.put('' + id + '/published', new PatchRequest(true))
  }
}
