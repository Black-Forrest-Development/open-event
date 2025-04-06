import {Component} from '@angular/core';
import {ActivityModule} from "../../activity/activity.module";

@Component({
  selector: 'app-account-activity',
  templateUrl: './account-activity.component.html',
  styleUrl: './account-activity.component.scss',
  imports: [
    ActivityModule
  ],
  standalone: true
})
export class AccountActivityComponent {

}
