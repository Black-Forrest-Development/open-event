import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {BaseService} from "../../shared/model/base-service";

@Injectable({
  providedIn: 'root'
})
export class ExportService extends BaseService {
  constructor(http: HttpClient) {
    super(http, 'export')
    this.retryCount = 1
  }

  exportEvents(): Observable<HttpResponse<Blob>> {
    return this.getBlob('event/pdf')
  }
}
