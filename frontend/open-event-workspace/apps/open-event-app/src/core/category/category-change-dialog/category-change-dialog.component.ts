import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle} from "@angular/material/dialog";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatFormField, MatHint, MatLabel} from "@angular/material/form-field";
import {MatIcon} from "@angular/material/icon";
import {TranslatePipe} from "@ngx-translate/core";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {Category, CategoryChangeRequest, CategoryService} from "@open-event-workspace/core";

@Component({
  selector: 'app-category-change-dialog',
  templateUrl: './category-change-dialog.component.html',
  styleUrl: './category-change-dialog.component.scss',
  imports: [
    MatFormField,
    MatIcon,
    TranslatePipe,
    MatInput,
    MatButton,
    ReactiveFormsModule,
    MatDialogContent,
    MatDialogTitle,
    MatDialogActions,
    MatLabel,
    MatHint,
  ],
  standalone: true
})
export class CategoryChangeDialogComponent {

  fg: FormGroup

  constructor(
    private fb: FormBuilder,
    private service: CategoryService,
    public dialogRef: MatDialogRef<CategoryChangeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Category | null
  ) {
    this.fg = this.fb.group({
      name: this.fb.control(data?.name ?? '', Validators.required),
      iconUrl: this.fb.control(data?.iconUrl ?? ''),
    })
  }

  onCancelClick(): void {
    this.dialogRef.close(false)
  }

  onSaveClick() {
    if (!this.fg.valid) {
      this.dialogRef.close(false)
    } else {
      let value = this.fg.value
      let request = new CategoryChangeRequest(value.name, value.iconUrl)
      let observable = (this.data) ? this.service.updateCategory(this.data.id, request) : this.service.createCategory(request)
      observable.subscribe({
        next: val => this.dialogRef.close(true),
        error: err => this.dialogRef.close(true),
      })
    }
  }
}
