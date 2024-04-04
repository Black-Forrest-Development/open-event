import {Injectable} from '@angular/core';
import {BaseService} from "../../shared/model/base-service";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AddressService extends BaseService {
  constructor(http: HttpClient) {
    super(http, 'address')
    this.retryCount = 0
  }
}
