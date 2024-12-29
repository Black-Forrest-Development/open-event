import {Component} from '@angular/core';
import {FormControl} from "@angular/forms";
import {EventBoardService} from "../model/event-board.service";
import {ChipSelectEntry} from "../../shared/chip-select-pane/chip-select-entry";
import {CategoryService} from "../../category/model/category.service";
import {MatChipSelectionChange} from "@angular/material/chips";
import {DateTime} from "luxon";

@Component({
    selector: 'app-event-board-filter',
    templateUrl: './event-board-filter.component.html',
    styleUrl: './event-board-filter.component.scss',
    standalone: false
})
export class EventBoardFilterComponent {


  reloadingCategories: boolean = false
  categoryForm = new FormControl([])
  allCategories: ChipSelectEntry[] = []

  constructor(public service: EventBoardService, private categoryService: CategoryService) {
  }

  onDateRangePickerClosed() {
    if (!this.service.range.valid) return
    this.service.handleRangeChanged()
  }


  selectToday() {
    let now = DateTime.now()
    this.selectRange(now, now)
  }

  selectThisWeek() {
    let now = DateTime.now()
    let start = now.startOf('week')
    let end = now.endOf('week')
    this.selectRange(start, end)
  }

  selectNextWeek() {
    let now = DateTime.now()

    let start = now.plus({ weeks: 1 }).startOf('week')

    let end = now.plus({ weeks: 1 }).endOf('week')
    this.selectRange(start, end)
  }

  private selectRange(start: DateTime, end: DateTime) {
    this.service.updateRange(start,end)
  }

  handleDatePreselectionChange(event: MatChipSelectionChange) {
    let selected = event.selected
    if (!selected) {
      this.service.updateRange(null, null)
    } else if(event.source.value === 'today') {
      this.selectToday()
    } else if(event.source.value === 'this_week') {
      this.selectThisWeek()
    } else if(event.source.value === 'next_week') {
      this.selectNextWeek()
    }
  }
}
