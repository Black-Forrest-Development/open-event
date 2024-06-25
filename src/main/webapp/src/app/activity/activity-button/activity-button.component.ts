import {Component, ViewChild} from '@angular/core';
import {ActivityService} from "../model/activity.service";
import {interval, Subscription} from "rxjs";
import {ActivityInfo} from "../model/activity-api";
import {MatMenuTrigger} from "@angular/material/menu";

@Component({
  selector: 'app-activity-button',
  templateUrl: './activity-button.component.html',
  styleUrl: './activity-button.component.scss'
})
export class ActivityButtonComponent {

  reloading: boolean = false
  data: ActivityInfo[] = []
  unreadInfos = 0
  @ViewChild('menuTrigger') menuTrigger: MatMenuTrigger | undefined

  private subscription: Subscription | undefined

  constructor(private service: ActivityService) {
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

  private handleData(value: ActivityInfo[]) {
    this.data = value
    this.unreadInfos = this.data.filter(d => !d.read).length
    this.reloading = false
    if (this.menuTrigger && this.data.length == 0) this.menuTrigger.closeMenu()
  }

  private handleError(err: any) {
    this.data = []
    this.unreadInfos = 0
    this.reloading = false
    if (this.menuTrigger) this.menuTrigger.closeMenu()
  }

  handleActivityClick(a: ActivityInfo) {
    this.reloading = true
    this.service.markRead(a.activity.id).subscribe(d => this.refresh())
  }

  private refresh() {
    this.reloading = true
    this.service.getUnreadActivityInfos().subscribe({
      next: value => this.handleData(value),
      error: err => this.handleError(err)
    })
  }
}
