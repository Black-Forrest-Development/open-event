import {Component, Inject} from '@angular/core';
import {EventService} from "@open-event-workspace/backoffice";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Event, EventChangeRequest} from "@open-event-workspace/core";

@Component({
  selector: 'app-event-change-dialog',
  imports: [],
  templateUrl: './event-change-dialog.component.html',
  styleUrl: './event-change-dialog.component.scss'
})
export class EventChangeDialogComponent {

  constructor(
    private service: EventService,
    public dialogRef: MatDialogRef<EventChangeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Event | undefined
  ) {
  }

  onCancelClick(): void {
    this.dialogRef.close(false)
  }

  handleRequest(request: EventChangeRequest) {
    let observable = (this.data) ? this.service.updateEvent(this.data.id, request) : this.service.createEvent(request)
    observable.subscribe({
      next: val => this.dialogRef.close(true),
      error: err => this.dialogRef.close(true),
    })
  }
}
