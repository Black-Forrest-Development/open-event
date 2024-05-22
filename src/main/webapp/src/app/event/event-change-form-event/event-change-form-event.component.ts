import {Component, Input} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {MatChipInputEvent} from "@angular/material/chips";


@Component({
  selector: 'app-event-change-form-event',
  templateUrl: './event-change-form-event.component.html',
  styleUrls: ['./event-change-form-event.component.scss']
})
export class EventChangeFormEventComponent {
  @Input() form: FormGroup | undefined
  @Input() hiddenFields: string[] = []


  constructor() {
  }

  get imageUrl() {
    return this.form?.get('imageUrl');
  }

  get iconUrl() {
    return this.form?.get('iconUrl');
  }

  get longText() {
    return this.form?.get('longText');
  }

  get shortText() {
    return this.form?.get('shortText');
  }

  get title() {
    return this.form?.get('title');
  }


  isVisible(ctrl: string): boolean {
    return this.hiddenFields.find(x => x == ctrl) == null
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
    return this.form?.get('tags') as FormControl;
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
