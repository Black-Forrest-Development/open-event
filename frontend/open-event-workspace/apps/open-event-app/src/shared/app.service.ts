import {Injectable, Signal} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {map, Subject} from "rxjs";
import {MatDialog} from "@angular/material/dialog";
import {Account, AccountInfo, AccountService, AccountValidationResult, Profile} from "@open-event-workspace/core";
import {AuthService} from "./auth/auth.service";
import {toSignal} from "@angular/core/rxjs-interop";
import {ConfirmLogoutDialogComponent} from "./confirm-logout-dialog/confirm-logout-dialog.component";

@Injectable({
  providedIn: 'root'
})
export class AppService {

  validated: Subject<boolean> = new Subject()
  account: Account | undefined
  profile: Profile | undefined
  info: AccountInfo | undefined
  lang: Signal<string>;


  constructor(
    private accountService: AccountService,
    private translate: TranslateService,
    public authService: AuthService,

    private dialog: MatDialog
  ) {
    this.lang = toSignal(this.translate.onLangChange.asObservable().pipe(map(value => value.lang)), {initialValue: translate.currentLang})
  }

  validate() {
    let l = this.lang() ?? this.translate.currentLang ?? "de"
    this.accountService.validate(l).subscribe(d => this.handleValidationResult(d))
  }

  private handleValidationResult(d: AccountValidationResult) {
    this.account = d.account
    this.profile = d.profile
    this.info = d.info
    this.translate.setDefaultLang('en')
    this.translate.use(d.profile.language)

    this.validated.next(true)
  }

  logout() {
    const dialogRef = this.dialog.open(ConfirmLogoutDialogComponent, {
      width: '250px',
      data: ''
    })
    dialogRef.afterClosed().subscribe(result => {
      if (result) this.authService.logout()
    })
  }
}
