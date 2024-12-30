import {Component, Input} from '@angular/core';
import {FormGroup, ReactiveFormsModule} from "@angular/forms";
import {MatOption, MatSelect, MatSelectChange} from "@angular/material/select";
import {NgIf} from "@angular/common";
import {TranslatePipe} from "@ngx-translate/core";
import {MatFormField} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {Account, Address, AddressService, Page} from "@open-event-workspace/core";
import {MatDivider} from "@angular/material/divider";

@Component({
  selector: 'app-event-change-form-location',
  templateUrl: './event-change-form-location.component.html',
  styleUrls: ['./event-change-form-location.component.scss'],
  imports: [
    NgIf,
    TranslatePipe,
    ReactiveFormsModule,
    MatFormField,
    MatInput,
    MatSelect,
    MatOption,
    MatDivider
  ],
  standalone: true
})
export class EventChangeFormLocationComponent {
  @Input() form: FormGroup | undefined
  @Input() hiddenFields: string[] = []
  @Input() account: Account | undefined
  reloading: boolean = false
  addresses: Address[] = []

  constructor(
    private addressService: AddressService
  ) {
  }

  ngOnInit() {
    this.reloading = true
    this.addressService.getAllForAccount(this.account?.id ?? -1, 0, 20).subscribe(d => this.handleAddresses(d))
  }

  isVisible(ctrl: string): boolean {
    return this.hiddenFields.find(x => x == ctrl) == null
  }

  handleAddressSelected(event: MatSelectChange) {
    let address = event.value as Address
    this.setAddress(address)
  }

  private handleAddresses(d: Page<Address>) {
    this.addresses = d.content
    if (this.addresses.length > 0 && !this.form?.dirty && !this.form?.valid) {
      this.setAddress(this.addresses[0])
    }
    this.reloading = false
  }

  private setAddress(address: Address) {
    if (!this.form) return
    this.form.setValue(
      {
        'street': address.street,
        'streetNumber': address.streetNumber,
        'zip': address.zip,
        'city': address.city,
        'country': address.country,
        'additionalInfo': address.additionalInfo,
      }
    )

  }
}
