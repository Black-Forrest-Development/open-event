import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Address} from "@open-event-workspace/core";
import {Observable} from "rxjs";
import {BaseService, Page} from "@open-event-workspace/shared";

@Injectable({
  providedIn: 'root'
})
export class AddressService extends BaseService {

  constructor(http: HttpClient) {
    super(http, 'app/address')
    this.retryCount = 1
  }

  getAddress(page: number, size: number): Observable<Page<Address>> {
    return this.getPaged('', page, size)
  }
}
