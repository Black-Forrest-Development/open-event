import {Component} from '@angular/core';
import {EventBoardService} from "../model/event-board.service";
import {HotToastService} from "@ngneat/hot-toast";

@Component({
  selector: 'app-event-board',
  templateUrl: './event-board.component.html',
  styleUrls: ['./event-board.component.scss']
})
export class EventBoardComponent {


  constructor(public service: EventBoardService, private toast: HotToastService) {
  }

  ngOnInit() {
    this.service.reload()
  }

}
