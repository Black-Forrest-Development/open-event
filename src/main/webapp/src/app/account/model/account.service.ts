import {Injectable} from '@angular/core';
import {BaseService} from "../../shared/model/base-service";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Account, AccountChangeRequest, AccountValidationResult} from "./account-api";
import {Page} from "../../shared/model/page";
import {Profile} from "../../profile/model/profile-api";
import {Preferences} from "../../preferences/model/preferences-api";

@Injectable({
  providedIn: 'root'
})
export class AccountService extends BaseService {
  constructor(http: HttpClient) {
    super(http, 'account')
    this.retryCount = 1
  }

  getAllAccounts(page: number, size: number): Observable<Page<Account>> {
    return this.getPaged('', page, size)
  }

  getAccount(id: number): Observable<Account> {
    return this.get('' + id)
  }

  createAccount(request: AccountChangeRequest): Observable<Account> {
    return this.post('', request)
  }

  validate(): Observable<AccountValidationResult> {
    return this.get('validate')
  }

  // @Deprecated
  searchAccounts(query: string, page: number, size: number): Observable<Page<Account>> {
    let params = new HttpParams().set("query", query)
    return this.getPaged('search', page, size, params)
  }

  getProfile(): Observable<Profile> {
    return this.get('profile')
  }

  getPreferences(): Observable<Preferences> {
    return this.get('preferences')
  }
}
