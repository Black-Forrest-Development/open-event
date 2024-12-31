import {Component, Input} from '@angular/core';
import {FormGroup, ReactiveFormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";
import {TranslatePipe} from "@ngx-translate/core";
import {MatError, MatFormField, MatHint, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatDatepicker, MatDatepickerInput, MatDatepickerToggle} from "@angular/material/datepicker";

@Component({
  selector: 'app-event-change-form-event',
  templateUrl: './event-change-form-event.component.html',
  styleUrls: ['./event-change-form-event.component.scss'],
  imports: [
    NgIf,
    TranslatePipe,
    ReactiveFormsModule,
    MatFormField,
    MatInput,
    MatDatepickerInput,
    MatDatepickerToggle,
    MatDatepicker,
    MatLabel,
    MatHint,
    MatError,
  ],
  standalone: true
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

}
