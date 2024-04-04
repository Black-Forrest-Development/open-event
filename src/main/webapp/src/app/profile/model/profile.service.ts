import {Injectable} from '@angular/core';
import {BaseService} from "../../shared/model/base-service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Profile, ProfileChangeRequest} from "./profile-api";

@Injectable({
  providedIn: 'root'
})
export class ProfileService extends BaseService {
  constructor(http: HttpClient) {
    super(http, 'profile')
    this.retryCount = 0
  }

  getOwnProfile(): Observable<Profile> {
    return this.get('own')
  }

  updateProfile(id: number, request: ProfileChangeRequest): Observable<Profile> {
    return this.put('' + id, request)
  }
}
