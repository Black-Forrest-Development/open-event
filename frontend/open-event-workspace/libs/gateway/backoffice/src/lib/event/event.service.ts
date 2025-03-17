import {Injectable} from "@angular/core";
import {BaseService, Page} from "@open-event-workspace/shared";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {EventChangeRequest} from "@open-event-workspace/core";


@Injectable({
  providedIn: 'root'
})
export class EventService extends BaseService {

  constructor(http: HttpClient) {
    super(http, 'backoffice/event')
    this.retryCount = 0
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

  updateEvent(id: number, request: EventChangeRequest): Observable<Event> {
    return this.put('' + id, request)
  }

  deleteEvent(id: number): Observable<Event> {
    return this.delete('' + id)
  }

}
