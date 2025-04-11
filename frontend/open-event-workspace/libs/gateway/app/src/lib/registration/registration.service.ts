import {Injectable} from "@angular/core";
import {BaseService} from "@open-event-workspace/shared";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Participant, ParticipateRequest, ParticipateResponse} from "@open-event-workspace/core";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService extends BaseService {

  constructor(http: HttpClient) {
    super(http, 'app/registration')
    this.retryCount = 1
  }

  getParticipants(id: number): Observable<Participant[]> {
    return this.get('' + id + '/participant')
  }

  addParticipant(id: number, request: ParticipateRequest): Observable<ParticipateResponse> {
    return this.post('' + id + '/participant', request)
  }

  changeParticipant(id: number, request: ParticipateRequest): Observable<ParticipateResponse> {
    return this.put('' + id + '/participant', request)
  }

  removeParticipant(id: number): Observable<ParticipateResponse> {
    return this.delete('' + id + '/participant')
  }
}
