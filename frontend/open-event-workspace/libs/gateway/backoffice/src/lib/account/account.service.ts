import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Account, AccountSearchRequest, AccountSearchResponse, AccountValidationResult} from "@open-event-workspace/core";
import {Observable} from "rxjs";
import {BaseService} from "@open-event-workspace/shared";

@Injectable({
  providedIn: 'root'
})
export class AccountService extends BaseService {

  constructor(http: HttpClient) {
    super(http, 'backoffice/account')
    this.retryCount = 0
  }

  getAccount(): Observable<Account> {
    return this.get('')
  }

  validate(lang: string): Observable<AccountValidationResult> {
    let params = new HttpParams()
      .set("lang", lang)
    return this.get('validate', params)
  }

  search(request: AccountSearchRequest, page: number, size: number): Observable<AccountSearchResponse> {
    let params = new HttpParams()
      .set("page", page)
      .set("size", size)
    return this.post('search', request, params)
  }

}
