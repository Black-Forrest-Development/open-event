import {Component, Input} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-event-change-form-registration',
  templateUrl: './event-change-form-registration.component.html',
  styleUrls: ['./event-change-form-registration.component.scss']
})
export class EventChangeFormRegistrationComponent {
  @Input() form: FormGroup | undefined
  @Input() hiddenFields: string[] = []

  isVisible(ctrl: string): boolean {
    return this.hiddenFields.find(x => x == ctrl) == null
  }

  get ticketsEnabled(): FormControl<any> {
    // @ts-ignore
    return this.form!!.get('ticketsEnabled')
  }
}
