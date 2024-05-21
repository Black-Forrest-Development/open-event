import {Injectable} from '@angular/core';
import {BaseService} from "../../shared/model/base-service";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ShareService extends BaseService {
  constructor(http: HttpClient) {
    super(http, 'share')
    this.retryCount = 0
  }
}
