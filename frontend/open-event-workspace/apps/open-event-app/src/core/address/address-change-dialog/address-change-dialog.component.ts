import {Component, Inject} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Address, AddressChangeRequest} from "@open-event-workspace/core";
import {TranslatePipe} from "@ngx-translate/core";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatIcon} from "@angular/material/icon";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-address-change-dialog',
  templateUrl: './address-change-dialog.component.html',
  styleUrl: './address-change-dialog.component.scss',
  imports: [
    MatDialogTitle,
    TranslatePipe,
    ReactiveFormsModule,
    MatFormField,
    MatDialogContent,
    MatInput,
    MatDialogActions,
    MatIcon,
    MatLabel,
    MatButton,
    MatDialogClose
  ],
  standalone: true
})
export class AddressChangeDialogComponent {

  fg: FormGroup

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Address | undefined,
    public dialogRef: MatDialogRef<AddressChangeDialogComponent>,
    fb: FormBuilder
  ) {
    this.fg = fb.group({
      street: [data?.street ?? '', Validators.required],
      streetNumber: [data?.streetNumber ?? '', Validators.required],
      zip: [data?.zip ?? '', Validators.required],
      city: [data?.city ?? '', Validators.required],
      country: [data?.country ?? 'Deutschland', Validators.required],
      additionalInfo: [data?.additionalInfo ?? ''],
      lat: [data?.lat ?? ''],
      lon: [data?.lon ?? ''],
    });
  }

  submit() {
    if (!this.fg.valid) return
    let value = this.fg.value
    this.dialogRef.close(value as AddressChangeRequest)
  }
}
