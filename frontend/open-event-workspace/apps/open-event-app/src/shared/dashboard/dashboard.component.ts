import {AfterViewInit, ChangeDetectorRef, Component, ViewChild} from '@angular/core';
import {NavigationEnd, Router, RouterLink, RouterOutlet} from "@angular/router";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";
import {filter, map, Observable, withLatestFrom} from "rxjs";
import {MatSidenav, MatSidenavContainer, MatSidenavContent} from "@angular/material/sidenav";
import {MainNavItem} from "./main-nav-item";
import {AuthService, MainMenuComponent} from "@open-event-workspace/shared";
import {DashboardService} from "./dashboard.service";
import {AsyncPipe} from "@angular/common";
import {DashboardToolbarComponent} from "../dashboard-toolbar/dashboard-toolbar.component";
import {Roles} from "../roles";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  imports: [
    MatSidenavContainer,
    AsyncPipe,
    MatSidenav,
    MatSidenavContent,
    RouterLink,
    MainMenuComponent,
    RouterOutlet,
    DashboardToolbarComponent
  ],
  standalone: true
})
export class DashboardComponent implements AfterViewInit {


  isHandset$: Observable<boolean>


  @ViewChild('drawer') drawer: MatSidenav | undefined

  navItems: MainNavItem[] = [
    new MainNavItem('/event', 'event.type', 'event_note'),
    new MainNavItem('/account', 'account.type', 'person', [Roles.ACCOUNT_READ]),
    new MainNavItem('/address', 'address.title', 'contact_mail', [Roles.ADDRESS_READ]),
    new MainNavItem('/activity', 'activity.title', 'notifications', [Roles.ACTIVITY_READ]),
    new MainNavItem('/category', 'category.type', 'label', [Roles.CATEGORY_WRITE]),
    new MainNavItem('/settings', 'settings.type', 'settings_applications', [Roles.SETTINGS_READ, Roles.SETTINGS_WRITE]),
    new MainNavItem('/mail', 'mail.type', 'email', [Roles.MAIL_READ, Roles.MAIL_WRITE]),
    new MainNavItem('/cache', 'cache.type', 'memory', [Roles.CACHE_READ, Roles.CACHE_WRITE]),
    new MainNavItem('/backoffice', 'backoffice.type', 'admin_panel_settings', [Roles.BACKOFFICE_ACCESS]),
    new MainNavItem('/history', 'history.type', 'history', [Roles.HISTORY_ADMIN]),


    // new MainNavItem('/inquiry', 'INQUIRY.Type', 'question_answer'),
    // new MainNavItem('/structure', 'STRUCT.Type', 'ballot'),
    // new MainNavItem('/profile', 'MENU.Profile', 'person'),
    // new MainNavItem('/administration', 'MENU.Administration', 'settings_applications'),
    // new MainNavItem('/imprint', 'MENU.Imprint', 'contact_support'),
  ]

  accessibleItems: MainNavItem[] = []

  constructor(
    public authService: AuthService,
    router: Router,
    private breakpointObserver: BreakpointObserver,
    private changeDetectorRef: ChangeDetectorRef,
    public service: DashboardService,
  ) {
    this.isHandset$ = this.breakpointObserver.observe(Breakpoints.Handset)
      .pipe(map(result => result.matches))

    router.events.pipe(
      withLatestFrom(this.isHandset$),
      filter(([a, b]) => b && a instanceof NavigationEnd)
    ).subscribe(_ => this.drawer?.close())
  }


  ngOnInit() {
    this.accessibleItems = this.navItems.filter(item => item.isAccessible(this.authService))

  }

  ngAfterViewInit() {
    this.changeDetectorRef.detectChanges();
  }


}
