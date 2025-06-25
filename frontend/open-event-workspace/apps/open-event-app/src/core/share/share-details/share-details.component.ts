import {Component, computed, input, output} from '@angular/core';
import {LoadingBarComponent} from "@open-event-workspace/shared";
import {EventInfo} from "@open-event-workspace/core";
import {MatCard} from "@angular/material/card";
import {TranslatePipe} from "@ngx-translate/core";
import {MatDivider} from "@angular/material/divider";
import {ShareButtons} from "ngx-sharebuttons/buttons";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-share-details',
  templateUrl: './share-details.component.html',
  styleUrl: './share-details.component.scss',
  imports: [
    MatCard,
    TranslatePipe,
    MatDivider,
    ShareButtons,
    LoadingBarComponent,
    MatButton
  ],
  standalone: true
})
export class ShareDetailsComponent {

  info = input.required<EventInfo>()
  reloading = input(false)
  share = computed(() => this.info().share)
  active = computed(() => this.share() && this.share()!!.share.enabled)
  title = computed(() => this.info().event.title)

  changed = output<boolean>()

  constructor() {
  }

  enableSharing() {
    this.changed.emit(true)
    // let share = this.share()
    // if (share && !share.enabled) {
    //   this.update(this.service.publish(share.id))
    // } else if (!share) {
    //   this.update(this.service.createShare(new ShareChangeRequest(this.event().id, true)))
    // }
  }

  disableSharing() {
    this.changed.emit(false)
    // let share = this.share()
    // if (!share) return
    // this.update(this.service.updateShare(share.id, new ShareChangeRequest(this.event().id, false)))
  }

}
