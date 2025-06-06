import {Injectable} from '@angular/core';
import {BaseService, Page, PatchRequest} from "@open-event-workspace/shared";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Event, EventChangeRequest, EventInfo, EventStats} from "./event.api";
import {DateTime} from 'luxon';
import {LocationChangeRequest} from "../location/location.api";
import {RegistrationChangeRequest} from "../registration/registration.api";

/**
 * @deprecated use gateway instead
 */
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

  getEventInfo(id: number): Observable<EventInfo> {
    return this.get('' + id + '/info')
  }

  getEventInfos(page: number, size: number): Observable<Page<EventInfo>> {
    return this.getPaged('info', page, size)
  }

  createEvent(request: EventChangeRequest): Observable<Event> {
    return this.post('', request)
  }

  createBackofficeEvent(accountId: number, request: EventChangeRequest): Observable<Event> {
    return this.post('' + accountId, request)
  }

  updateEvent(id: number, request: EventChangeRequest): Observable<Event> {
    return this.put('' + id, request)
  }

  publish(id: number): Observable<Event> {
    return this.put('' + id + '/published', new PatchRequest(true))
  }

  deleteEvent(id: number): Observable<Event> {
    return this.delete('' + id)
  }


  getStats(): Observable<EventStats[]> {
    return this.get('stats')
  }


  createRequest(value: any, endHidden: boolean): EventChangeRequest | undefined {
    let start = this.createDateTime(value.event.startTime, value.event.startDate);
    let end = endHidden ? this.createDateTime(value.event.endTime, value.event.startDate) : this.createDateTime(value.event.endTime, value.event.endDate);
    if (!start || !end) return undefined

    let location = new LocationChangeRequest(
      value.location.street,
      value.location.streetNumber,
      value.location.zip,
      value.location.city,
      value.location.country,
      value.location.additionalInfo,
      0.0,
      0.0,
      -1
    )
    let registration = new RegistrationChangeRequest(
      value.registration.maxGuestAmount,
      value.registration.interestedAllowed,
      value.registration.ticketsEnabled
    )


    return new EventChangeRequest(
      start.toFormat("YYYY-MM-DD[T]HH:mm:ss"),
      end.toFormat("YYYY-MM-DD[T]HH:mm:ss"),
      value.event.title,
      value.event.shortText,
      value.event.longText,
      value.event.imageUrl,
      value.event.iconUrl,
      value.registration.categories,
      location,
      registration,
      true,
      value.registration.tags
    )
  }

  private createDateTime(timeStr: string, date: any): DateTime | undefined {
    let mDate = DateTime.fromISO(date)
    let time = timeStr.split(":");
    if (time.length == 2 && mDate.isValid) {
      mDate = mDate.set({hour: parseInt(time[0]), minute: parseInt(time[1])});
      return mDate
    }
    return undefined;
  }
}
