import {Component, ViewChild} from '@angular/core';
import {interval, Subscription} from "rxjs";
import {MatMenu, MatMenuTrigger} from "@angular/material/menu";
import {Router, RouterLink} from "@angular/router";
import {MatButton, MatIconAnchor, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatDivider} from "@angular/material/divider";
import {TranslatePipe} from "@ngx-translate/core";
import {DatePipe} from "@angular/common";
import {MatBadge} from "@angular/material/badge";
import {Activity, ActivityInfo, ActivityService} from "@open-event-workspace/core";

@Component({
  selector: 'app-activity-button',
  templateUrl: './activity-button.component.html',
  styleUrl: './activity-button.component.scss',
  imports: [
    MatIconAnchor,
    RouterLink,
    MatIcon,
    MatMenuTrigger,
    MatMenu,
    MatDivider,
    TranslatePipe,
    MatButton,
    DatePipe,
    MatBadge,
    MatIconButton
  ],
  standalone: true
})
export class ActivityButtonComponent {

  reloading: boolean = false
  data: ActivityInfo[] = []
  unreadInfos = 0
  @ViewChild('menuTrigger') menuTrigger: MatMenuTrigger | undefined

  private subscription: Subscription | undefined

  constructor(private service: ActivityService, private router: Router) {
  }

  ngOnInit() {
    this.reload()
    this.subscription = interval(10000).subscribe(value => this.reload())
  }

  ngOnDestroy() {
    if (this.subscription) this.subscription.unsubscribe()
  }

  private reload() {
    if (this.reloading) return
    this.refresh()
  }


  handleActivityClick(a: ActivityInfo) {
    this.service.markRead(a.activity.id).subscribe({
      next: value => this.navigateToSource(value),
      error: err => this.handleError(err)
    })
  }

  private navigateToSource(activity: Activity) {
    if (activity.source === 'EVENT' || activity.source === 'REGISTRATION') {
      this.router.navigate(['event', 'details', activity.sourceId]).then()
      this.refresh()
    } else {
      this.refresh()
    }
  }

  private refresh() {
    if (this.reloading) return
    this.reloading = true
    this.service.getUnreadActivityInfos().subscribe({
      next: value => this.handleData(value),
      error: err => this.handleError(err)
    })
  }

  handleMarkAllReadClick() {
    this.reloading = true
    this.service.markAllRead().subscribe({
      next: value => this.handleData(value),
      error: err => this.handleError(err)
    })
  }


  private handleData(value: ActivityInfo[]) {
    this.data = value
    this.unreadInfos = this.data.filter(d => !d.read).length
    this.reloading = false
    if (this.menuTrigger && this.unreadInfos == 0) this.menuTrigger.closeMenu()
  }

  private handleError(err: any) {
    this.data = []
    this.unreadInfos = 0
    this.reloading = false
    if (this.menuTrigger) this.menuTrigger.closeMenu()
  }
}
