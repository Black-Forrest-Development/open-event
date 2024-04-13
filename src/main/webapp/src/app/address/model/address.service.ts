import {Injectable} from '@angular/core';
import {BaseService} from "../../shared/model/base-service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../../shared/model/page";
import {Address, AddressChangeRequest} from "./address-api";

@Injectable({
  providedIn: 'root'
})
export class AddressService extends BaseService {
  constructor(http: HttpClient) {
    super(http, 'address')
    this.retryCount = 0
  }

  getAllAddresses(page: number, size: number): Observable<Page<Address>> {
    return this.getPaged('', page, size)
  }

  getAddress(id: number): Observable<Address> {
    return this.get('' + id)
  }

  createAddress(request: AddressChangeRequest): Observable<Address> {
    return this.post('', request)
  }

  updateAddress(id: number, request: AddressChangeRequest): Observable<Address> {
    return this.put('' + id, request)
  }

  importAddress(): Observable<Page<Address>> {
    return this.post('import', {})
  }

}
