import {Component, EventEmitter, Input, Output} from '@angular/core';
import {AccountInfo} from "../../account/model/account-api";

@Component({
  selector: 'app-dashboard-toolbar',
  templateUrl: './dashboard-toolbar.component.html',
  styleUrl: './dashboard-toolbar.component.scss'
})
export class DashboardToolbarComponent {
  @Input() mobileView: boolean = false
  @Input() title: string = ""
  @Input() account: AccountInfo | undefined
  @Output() toggleSidenavEvent = new EventEmitter<boolean>()

}
