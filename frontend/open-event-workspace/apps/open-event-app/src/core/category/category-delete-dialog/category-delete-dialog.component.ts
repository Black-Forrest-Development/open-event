import {Component, Inject} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {MatIcon} from "@angular/material/icon";
import {TranslatePipe} from "@ngx-translate/core";
import {MatButton} from "@angular/material/button";
import {Category, CategoryService} from "@open-event-workspace/core";

@Component({
  selector: 'app-category-delete-dialog',
  templateUrl: './category-delete-dialog.component.html',
  styleUrl: './category-delete-dialog.component.scss',
  imports: [
    MatIcon,
    TranslatePipe,
    MatButton,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions
  ],
  standalone: true
})
export class CategoryDeleteDialogComponent {
  constructor(
    private service: CategoryService,
    public dialogRef: MatDialogRef<CategoryDeleteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Category
  ) {

  }

  onNoClick(): void {
    this.dialogRef.close(false)
  }

  onYesClick() {
    this.service.deleteCategory(this.data.id).subscribe(
      {
        next: val => this.dialogRef.close(true),
        error: err => this.dialogRef.close(true),
      }
    )
  }
}
