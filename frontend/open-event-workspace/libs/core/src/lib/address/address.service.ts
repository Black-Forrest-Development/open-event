import {Injectable} from '@angular/core';
import {BaseService} from "../base-service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../page";
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


  deleteAddress(id: number): Observable<Address> {
    return this.delete('' + id)
  }

  importAddress(): Observable<Page<Address>> {
    return this.post('import', {})
  }

  getAllForAccount(accountId: number, page: number, size: number): Observable<Page<Address>> {
    return this.getPaged('for/account/' + accountId, page, size)
  }

}
