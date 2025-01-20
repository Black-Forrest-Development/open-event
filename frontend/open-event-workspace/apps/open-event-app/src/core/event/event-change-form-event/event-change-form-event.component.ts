import {Component, Input} from '@angular/core';
import {FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CommonModule, NgIf} from "@angular/common";
import {TranslatePipe} from "@ngx-translate/core";
import {MatError, MatFormField, MatFormFieldModule, MatHint, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatDatepickerInput, MatDatepickerModule, MatDatepickerToggle} from "@angular/material/datepicker";
import {provideLuxonDateAdapter} from "@angular/material-luxon-adapter";

@Component({
  selector: 'app-event-change-form-event',
  templateUrl: './event-change-form-event.component.html',
  providers: [provideLuxonDateAdapter()],
  styleUrls: ['./event-change-form-event.component.scss'],
  imports: [
    NgIf,
    CommonModule,
    TranslatePipe,
    ReactiveFormsModule,
    MatFormField,
    MatInput,
    MatDatepickerInput,
    MatDatepickerToggle,
    MatDatepickerModule,
    MatLabel,
    MatHint,
    MatError,
    MatFormFieldModule, MatDatepickerModule, FormsModule
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
