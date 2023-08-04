import {Component} from '@angular/core';
import {EventBoardService} from "../model/event-board.service";

@Component({
  selector: 'app-event-board',
  templateUrl: './event-board.component.html',
  styleUrls: ['./event-board.component.scss']
})
export class EventBoardComponent {


  constructor(public service: EventBoardService) {
  }

  ngOnInit() {
    this.service.reload()
  }

}
