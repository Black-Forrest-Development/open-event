import {Component} from '@angular/core';
import {TranslatePipe} from "@ngx-translate/core";
import {MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle} from "@angular/material/dialog";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-confirm-logout-dialog',
  templateUrl: './confirm-logout-dialog.component.html',
  styleUrls: ['./confirm-logout-dialog.component.scss'],
  imports: [
    TranslatePipe,
    MatDialogActions,
    MatDialogClose,
    MatDialogTitle,
    MatDialogContent,
    MatButton
  ],
  standalone: true
})
export class ConfirmLogoutDialogComponent {

}
