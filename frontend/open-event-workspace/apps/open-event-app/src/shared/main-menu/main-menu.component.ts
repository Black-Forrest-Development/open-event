import {Component, input} from '@angular/core';
import {MainNavItem} from "../dashboard/main-nav-item";
import {MatDialog} from "@angular/material/dialog";
import {AppService} from "../app.service";
import {RouterLink, RouterLinkActive} from "@angular/router";
import {MatIcon} from "@angular/material/icon";
import {TranslatePipe} from "@ngx-translate/core";

@Component({
  selector: 'app-main-menu',
  templateUrl: './main-menu.component.html',
  styleUrl: './main-menu.component.scss',
  imports: [
    RouterLink,
    RouterLinkActive,
    MatIcon,
    TranslatePipe
  ],
  standalone: true
})
export class MainMenuComponent {

  accessibleItems = input<MainNavItem[]>([])

  constructor(public service: AppService, private dialog: MatDialog,) {
  }

  logout() {
    this.service.logout()
  }
}
