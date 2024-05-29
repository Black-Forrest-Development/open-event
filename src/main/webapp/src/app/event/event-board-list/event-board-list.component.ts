import {Component, ElementRef, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {EventBoardService} from "../model/event-board.service";

@Component({
  selector: 'app-event-board-list',
  templateUrl: './event-board-list.component.html',
  styleUrls: ['./event-board-list.component.scss']
})
export class EventBoardListComponent implements OnInit, OnDestroy {

  @Input() options = {};
  @ViewChild('anchor', {static: true}) anchor: ElementRef<HTMLElement> | undefined
  private observer: IntersectionObserver | undefined

  filterOverlayOpen: boolean = false

  constructor(public service: EventBoardService, private host: ElementRef) {
    this.filterOverlayOpen = service.filterToolbarVisible
  }

  get element() {
    return this.host.nativeElement;
  }

  ngOnInit() {
    const options = {
      root: this.isHostScrollable() ? this.host.nativeElement : null,
      ...this.options
    }
    if (!this.anchor) return

    this.observer = new IntersectionObserver(([entry]) => {
      if (entry.isIntersecting) {
        this.service.onScroll()
      }
    }, options);

    this.observer.observe(this.anchor.nativeElement)
  }

  private isHostScrollable() {
    const style = window.getComputedStyle(this.element);

    return style.getPropertyValue('overflow') === 'auto' ||
      style.getPropertyValue('overflow-y') === 'scroll';
  }

  ngOnDestroy() {
    if (this.observer) this.observer.disconnect()
  }

}
