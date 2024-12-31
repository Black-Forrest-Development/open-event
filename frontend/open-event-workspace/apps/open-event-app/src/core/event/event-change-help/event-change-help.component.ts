import {Component, Input} from '@angular/core';
import {NgOptimizedImage, NgSwitch, NgSwitchCase, NgSwitchDefault} from "@angular/common";
import {MatCard, MatCardContent, MatCardHeader} from "@angular/material/card";
import {MatIcon} from "@angular/material/icon";
import {MatDivider} from "@angular/material/divider";

@Component({
  selector: 'app-event-change-help',
  templateUrl: './event-change-help.component.html',
  styleUrls: ['./event-change-help.component.scss'],
  imports: [
    NgSwitch,
    MatCard,
    MatCardHeader,
    MatIcon,
    MatDivider,
    MatCardContent,
    NgOptimizedImage,
    NgSwitchCase,
    NgSwitchDefault
  ],
  standalone: true
})
export class EventChangeHelpComponent {
  @Input() step: number = 0
}
