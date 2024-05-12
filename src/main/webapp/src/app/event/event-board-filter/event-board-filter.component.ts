import {Component} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {EventBoardService} from "../model/event-board.service";
import {ChipSelectEntry} from "../../shared/chip-select-pane/chip-select-entry";
import {CategoryService} from "../../category/model/category.service";

@Component({
  selector: 'app-event-board-filter',
  templateUrl: './event-board-filter.component.html',
  styleUrl: './event-board-filter.component.scss'
})
export class EventBoardFilterComponent {

  range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  })


  reloadingCategories: boolean = false
  categoryForm = new FormControl([])
  allCategories: ChipSelectEntry[] = []

  constructor(public service: EventBoardService, private categoryService: CategoryService) {
  }

  onDateRangePickerClosed() {
    if (!this.range.valid) return
    this.search()
  }


  selectToday() {
    let now = new Date()
    let start = new Date(now)
    let end = new Date(now)
    this.selectRange(start, end)
  }

  selectThisWeek() {
    let now = new Date()
    let currentDay = now.getDay()

    let start = new Date(now)
    start.setDate(now.getDate() - currentDay)

    let end = new Date(now)
    end.setDate(now.getDate() + (6 - currentDay))
    this.selectRange(start, end)
  }

  selectNextWeek() {
    let now = new Date()
    let currentDay = now.getDay()

    let start = new Date(now)
    start.setDate(now.getDate() - currentDay + 7)

    let end = new Date(now)
    end.setDate(now.getDate() + (6 - currentDay) + 7)
    this.selectRange(start, end)
  }

  private selectRange(start: Date, end: Date) {
    start.setUTCHours(0, 0, 0, 0)
    end.setUTCHours(23, 59, 59, 999)
    this.range.setValue(
      {
        start: start,
        end: end,
      }
    )
    this.search()
  }

  private search() {
    let value = this.range.value
    this.service.range = {start: value.start, end: value.end}
  }
}
