import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {EventBoardService} from "../model/event-board.service";
import {debounceTime, distinctUntilChanged} from "rxjs";
import {FormControl, FormGroup} from "@angular/forms";
import {ChipSelectEntry} from "../../shared/chip-select-pane/chip-select-entry";
import {CategoryService} from "../../category/model/category.service";
import {EventSearchRequest} from "../../search/model/search-api";

@Component({
  selector: 'app-event-board-header',
  templateUrl: './event-board-header.component.html',
  styleUrls: ['./event-board-header.component.scss']
})
export class EventBoardHeaderComponent implements OnInit {

  keyUp: EventEmitter<string> = new EventEmitter<string>()
  @Input() mobileView: boolean = false
  @Input() mode: string = ''

  @Output() modeChanged = new EventEmitter<string>

  filterToolbarVisible: boolean = false

  range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });

  reloadingCategories: boolean = false
  categoryForm = new FormControl([])
  allCategories: ChipSelectEntry[] = []

  private _fullTextSearch: string = ''
  set fullTextSearch(val: string) {
    if (this._fullTextSearch === val) return
    this._fullTextSearch = val
    this.search()
  }

  get fullTextSearch(): string {
    return this._fullTextSearch
  }

  private _showHistory: boolean = false
  set showHistory(val: boolean) {
    if (this._showHistory == val) return
    this._showHistory = val
    this.search()
  }

  get showHistory(): boolean {
    return this._showHistory
  }

  private _ownEvents: boolean = false
  set ownEvents(val: boolean) {
    if (this._ownEvents == val) return
    this._ownEvents = val
    this.search()
  }

  get ownEvents(): boolean {
    return this._ownEvents
  }

  private _availableEvents: boolean = false
  set availableEvents(val: boolean) {
    if (this._availableEvents == val) return
    this._availableEvents = val
    this.search()
  }

  get availableEvents(): boolean {
    return this._availableEvents
  }


  private _participatingEvents: boolean = false
  set participatingEvents(val: boolean) {
    if (this._participatingEvents == val) return
    this._participatingEvents = val
    this.search()
  }

  get participatingEvents(): boolean {
    return this._participatingEvents
  }

  constructor(public service: EventBoardService, private categoryService: CategoryService) {
  }

  ngOnInit() {
    this.keyUp.pipe(
      debounceTime(500),
      distinctUntilChanged()
    ).subscribe(query => this.fullTextSearch = query)
    this.search()
  }

  onDateRangePickerClosed() {
    if (!this.range.valid) return
    this.search()
  }


  private search() {
    let rangeValue = this.range.value

    let startDate = null
    if (rangeValue.start) {
      startDate = new Date(rangeValue.start)
      startDate.setUTCHours(0, 0, 0, 0)
    } else if (!this._showHistory) {
      startDate = new Date()
      startDate.setUTCHours(0, 0, 0, 0)
    }

    let endDate = null
    if (rangeValue.end) {
      endDate = new Date(rangeValue.end)
      endDate.setUTCHours(23, 59, 59, 999)
    }


    let from = startDate?.toISOString()
    let to = endDate?.toISOString()

    let request = new EventSearchRequest(this.fullTextSearch, from, to, this.ownEvents, this.participatingEvents, this.availableEvents)
    this.service.search(request)
  }

  resetFilter() {
    this.range.reset()
    this._fullTextSearch = ''
    this._showHistory = false
    this.filterToolbarVisible = false
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

}
