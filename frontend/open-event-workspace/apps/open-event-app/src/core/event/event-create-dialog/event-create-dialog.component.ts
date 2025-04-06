import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Account, AccountService} from "@open-event-workspace/core";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {EventChangeComponent} from "../event-change/event-change.component";

@Component({
  selector: 'app-event-create-dialog',
  templateUrl: './event-create-dialog.component.html',
  styleUrl: './event-create-dialog.component.scss',
  imports: [
    MatProgressSpinner,
    EventChangeComponent
  ],
  standalone: true
})
export class EventCreateDialogComponent {

  account: Account | undefined
  loading: boolean = false

  constructor(
    public dialogRef: MatDialogRef<EventCreateDialogComponent>,
    private accountService: AccountService,
    @Inject(MAT_DIALOG_DATA) public data: number | undefined,
  ) {
    if (data) {
      this.loading = true
      this.accountService.getAccount(data).subscribe({
        next: value => {
          this.account = value
          this.loading = false
        }
      })
    }
  }
}
