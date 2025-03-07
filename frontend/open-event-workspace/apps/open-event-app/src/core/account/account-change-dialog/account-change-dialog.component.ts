import {Component, Inject} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TranslatePipe} from "@ngx-translate/core";
import {ReactiveFormsModule} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle} from "@angular/material/dialog";
import {MatIcon} from "@angular/material/icon";
import {MatButton} from "@angular/material/button";
import {AccountChangeComponent, AccountSearchEntry, AccountService, AccountSetupRequest} from "@open-event-workspace/core";

@Component({
  selector: 'app-account-change-dialog',
  imports: [CommonModule, TranslatePipe, ReactiveFormsModule, MatDialogActions, MatIcon, MatDialogClose, MatButton, MatDialogTitle, MatDialogContent, AccountChangeComponent],
  templateUrl: './account-change-dialog.component.html',
  styleUrl: './account-change-dialog.component.scss',
})
export class AccountChangeDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<AccountChangeDialogComponent>,
    private service: AccountService,
    @Inject(MAT_DIALOG_DATA) public data: AccountSearchEntry | undefined
  ) {

  }

  handleRequest(request: AccountSetupRequest) {
    let observable = (this.data) ? this.service.updateAccount(this.data.id, request) : this.service.setupAccount(request)
    observable.subscribe({
      next: val => this.dialogRef.close(true),
      error: err => this.dialogRef.close(true),
    })
  }
}
