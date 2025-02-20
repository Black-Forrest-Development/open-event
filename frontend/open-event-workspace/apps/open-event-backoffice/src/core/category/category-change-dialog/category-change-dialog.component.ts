import {Component, Inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {CategoryService} from "@open-event-workspace/backoffice";
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {Category, CategoryChangeRequest} from "@open-event-workspace/core";
import {CommonModule} from "@angular/common";
import {TranslatePipe} from "@ngx-translate/core";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";

@Component({
  selector: 'app-category-change-dialog',
  imports: [CommonModule, MatDialogModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatIconModule, TranslatePipe, ReactiveFormsModule],
  templateUrl: './category-change-dialog.component.html',
  styleUrl: './category-change-dialog.component.scss'
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
