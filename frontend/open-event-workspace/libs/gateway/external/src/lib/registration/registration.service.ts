import {Injectable} from '@angular/core';
import {BaseService} from "@open-event-workspace/shared";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ParticipantAddRequest, ParticipateRequest} from "@open-event-workspace/core";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService extends BaseService {

  constructor(http: HttpClient) {
    super(http, 'external/registration')
    this.retryCount = 0
  }

  requestParticipation(id: string, request: ParticipantAddRequest): Observable<any> {
    return this.post(id + '/participant', request)
  }

  changeParticipation(id: string, participantId: string, request: ParticipateRequest): Observable<any> {
    return this.put(id + '/participant/' + participantId, request)
  }

  cancelParticipation(id: string, participantId: string): Observable<any> {
    return this.delete(id + '/participant/' + participantId)
  }

  confirmParticipation(id: string, participantId: string): Observable<any> {
    return this.post(id + '/participant/' + participantId, {})
  }

}
