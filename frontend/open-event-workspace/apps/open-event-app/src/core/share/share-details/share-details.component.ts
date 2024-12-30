import {Component, Input} from '@angular/core';
import {AuthService} from "../../../shared/auth/auth.service";
import {MatSlideToggle, MatSlideToggleChange} from "@angular/material/slide-toggle";
import {Event, Share, ShareChangeRequest, ShareService} from "@open-event-workspace/core";
import {MatCard} from "@angular/material/card";
import {TranslatePipe} from "@ngx-translate/core";
import {MatDivider} from "@angular/material/divider";
import {MatProgressBar} from "@angular/material/progress-bar";
import {ShareButtons} from "ngx-sharebuttons/buttons";

@Component({
  selector: 'app-share-details',
  templateUrl: './share-details.component.html',
  styleUrl: './share-details.component.scss',
  imports: [
    MatCard,
    TranslatePipe,
    MatSlideToggle,
    MatDivider,
    MatProgressBar,
    ShareButtons
  ],
  standalone: true
})
export class ShareDetailsComponent {

  @Input()
  set data(value: Event) {
    this.event = value
    this.updateData()
  }

  event: Event | undefined
  share: Share | undefined
  reloading: boolean = false
  adminOrManager: boolean = false

  constructor(
    private service: ShareService,
    private authService: AuthService
  ) {
  }

  ngOnInit() {
    this.adminOrManager = this.authService.hasRole(AuthService.REGISTRATION_MANAGE, AuthService.REGISTRATION_ADMIN)
    let principal = this.authService.getPrincipal()
    if (principal) this.adminOrManager = principal.roles.find(r => r === 'openevent.registration.manage' || r === 'openevent.registration.admin') != null
  }

  private updateData() {
    if (!this.event) return
    this.reloading = true
    this.service.findByEvent(this.event.id).subscribe({
      next: value => this.handleData(value),
      error: err => this.handleError(err)
    })
  }

  private handleData(d: Share) {
    this.share = d
    this.reloading = false
  }

  private handleError(err: any) {
    this.reloading = false
  }

  handleToggleChanged(event: MatSlideToggleChange) {
    if (event.checked) {
      this.enableSharing()
    } else {
      this.disableSharing()
    }
  }

  private enableSharing() {
    if (!this.event) return
    if (this.share && !this.share.published) {
      this.reloading = true
      this.service.publish(this.share.id).subscribe(
        {
          next: value => this.handleData(value),
          error: err => this.handleError(err)
        }
      )
    } else if (!this.share) {
      this.reloading = true
      this.service.createShare(new ShareChangeRequest(this.event.id, true)).subscribe(
        {
          next: value => this.handleData(value),
          error: err => this.handleError(err)
        }
      )
    }
  }

  private disableSharing() {
    if (!this.event) return
    if (!this.share) return
    this.reloading = true
    this.service.updateShare(this.share.id, new ShareChangeRequest(this.event.id, false)).subscribe(
      {
        next: value => this.handleData(value),
        error: err => this.handleError(err)
      }
    )
  }
}
