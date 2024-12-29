import {Component, EventEmitter, input, Output} from '@angular/core';
import {AppService} from "../app.service";

@Component({
  selector: 'app-dashboard-toolbar',
  templateUrl: './dashboard-toolbar.component.html',
  styleUrl: './dashboard-toolbar.component.scss',
  standalone: true
})
export class DashboardToolbarComponent {
  mobileView = input<boolean>(false)
  title = input<string>("")
  @Output() toggleSidenavEvent = new EventEmitter<boolean>()

  constructor(
    public service: AppService
  ) {
  }

  logout() {
    this.service.logout()
  }
}
