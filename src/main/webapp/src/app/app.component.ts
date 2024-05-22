import {Component} from '@angular/core';
import {AccountService} from "./account/model/account.service";
import {Account, AccountValidationResult} from "./account/model/account-api";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {LoadingScreenComponent} from "./dashboard/loading-screen/loading-screen.component";
import {environment} from './../environments/environment';
import LogRocket from 'logrocket';
import {TranslateService} from "@ngx-translate/core";
import {AuthService} from "./auth/auth.service";
import {Location} from '@angular/common';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'open-event';

  validated: boolean = false
  account: Account | undefined
  dialogRef: MatDialogRef<any> | undefined

  constructor(
    private accountService: AccountService,
    private translate: TranslateService,
    private location: Location,
    private authService: AuthService,
    private dialog: MatDialog
  ) {

    if (environment.logrocket && environment.logrocketAppId.length > 0) {
      LogRocket.init(environment.logrocketAppId)
    }
  }

  ngOnInit() {
    let url = this.location.path()
    if (url.includes("share/info")) return

    this.dialogRef = this.dialog.open(LoadingScreenComponent, {disableClose: true})
    this.accountService.validate().subscribe(d => this.handleValidationResult(d))

  }

  private handleValidationResult(d: AccountValidationResult) {
    this.account = d.account
    this.translate.setDefaultLang('en')
    this.translate.use(d.language)

    this.validated = true
    if (environment.logrocket) {
      LogRocket.identify(d.account.id + '')
    }
    this.dialogRef?.close()
  }
}
