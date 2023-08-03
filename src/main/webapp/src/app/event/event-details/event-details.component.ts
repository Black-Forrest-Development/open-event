import {Component} from '@angular/core';
import {EventService} from "../model/event.service";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {Event} from "../model/event-api";
import {Location} from "@angular/common";
import {HotToastService} from "@ngneat/hot-toast";
import {ThemePalette} from "@angular/material/core";
import {EventMenuComponent} from "../event-menu/event-menu.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.scss']
})
export class EventDetailsComponent {

  reloading: boolean = false

  event: Event | undefined
  menu: EventMenuComponent

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private location: Location,
    private service: EventService,
    private toastService: HotToastService,
    public dialog: MatDialog,
  ) {
    this.menu = new EventMenuComponent(router, dialog, service, toastService)
  }

  ngOnInit() {
    this.route.paramMap.subscribe(p => this.handleParams(p))
  }

  private handleParams(p: ParamMap) {
    let idParam = p.get('id')
    let id = idParam !== null ? +idParam : null
    if (!id) return

    this.reloading = true;
    this.service.getEvent(id).subscribe(d => this.handleData(d))

  }

  private handleData(d: Event) {
    this.event = d
    this.menu.data = d
    this.reloading = false
  }

  back() {
    this.location.back()
  }


  links = ['First', 'Second', 'Third'];
  activeLink = this.links[0];
  background: ThemePalette = undefined;

  edit() {

  }

  delete() {

  }

  copy() {

  }
}
