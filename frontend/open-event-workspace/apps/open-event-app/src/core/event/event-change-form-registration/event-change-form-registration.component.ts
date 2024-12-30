import {Component, Input} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {MatChipGrid, MatChipInput, MatChipInputEvent, MatChipRow} from "@angular/material/chips";
import {ChipSelectEntry} from "../../../shared/chip-select-pane/chip-select-entry";
import {Category, CategoryService, Page} from "@open-event-workspace/core";
import {NgIf} from "@angular/common";
import {MatIcon} from "@angular/material/icon";
import {TranslatePipe} from "@ngx-translate/core";
import {MatFormField} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatOption, MatSelect} from "@angular/material/select";
import {MatProgressBar} from "@angular/material/progress-bar";
import {ChipSelectPaneComponent} from "../../../shared/chip-select-pane/chip-select-pane.component";

@Component({
  selector: 'app-event-change-form-registration',
  templateUrl: './event-change-form-registration.component.html',
  styleUrls: ['./event-change-form-registration.component.scss'],
  imports: [
    NgIf,
    MatIcon,
    TranslatePipe,
    ReactiveFormsModule,
    MatFormField,
    MatInput,
    MatSelect,
    MatOption,
    MatProgressBar,
    ChipSelectPaneComponent,
    MatChipInput,
    MatChipGrid,
    MatChipRow
  ],
  standalone: true
})
export class EventChangeFormRegistrationComponent {
  fg: FormGroup | undefined
  @Input() hiddenFields: string[] = []
  @Input() categoryForm: FormControl | undefined
  allCategories: ChipSelectEntry[] = []
  categoriesLoading: boolean = false

  constructor(private categoryService: CategoryService) {
  }

  @Input()
  set form(value: FormGroup) {
    this.fg = value
    this.categoryForm = value.get('categories') as FormControl
  }

  get ticketsEnabled(): FormControl<any> {
    // @ts-ignore
    return this.form!!.get('ticketsEnabled')
  }

  ngOnInit() {
    this.categoriesLoading = true
    this.categoryService.getAllCategories(0, 100).subscribe(d => this.handleCategoryData(d))
  }

  isVisible(ctrl: string): boolean {
    return this.hiddenFields.find(x => x == ctrl) == null
  }

  private handleCategoryData(d: Page<Category>) {
    this.allCategories = d.content.map(d => ({id: d.id, name: d.name} as ChipSelectEntry))
    this.categoriesLoading = false
  }

  addTag(event: MatChipInputEvent) {
    const value = (event.value || '').trim()
    if (value.length <= 0) return
    const t = this.tags
    if (value && t) {
      let data = t.value as string[]
      let index = data.indexOf(value)
      if (index < 0) data.push(value)
    }
    event.chipInput!.clear();
  }

  get tags(): FormControl {
    return this.fg?.get('tags') as FormControl;
  }

  removeTag(tag: string) {
    let t = this.tags
    if (!t) return

    let index = (t.value as string[]).indexOf(tag)
    if (index >= 0) {
      (t.value as string[]).splice(index, 1)
    }
  }
}
