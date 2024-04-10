import {Component} from '@angular/core';
import {Address} from "../../address/model/address-api";
import {AddressService} from "../../address/model/address.service";
import {Page} from "../../shared/model/page";

@Component({
  selector: 'app-account-address',
  templateUrl: './account-address.component.html',
  styleUrl: './account-address.component.scss'
})
export class AccountAddressComponent {

  address: Address[] = []
  reloading: boolean = false
  pageSize: number = 20
  pageIndex: number = 0
  totalSize: number = 0

  constructor(private service: AddressService) {
  }

  ngOnInit() {
    this.reload()
  }

  private reload() {
    if (this.reloading) return
    this.reloading = true
    this.service.getAllAddresses(this.pageIndex, this.pageSize).subscribe({
      next: value => this.handleData(value),
      error: e => this.handleError(e)
    })
  }

  private handleData(value: Page<Address>) {
    this.address = value.content
    this.pageSize = value.pageable.size
    this.pageIndex = value.pageable.number
    this.totalSize = value.totalSize
    this.reloading = false
  }

  private handleError(e: any) {
    this.reloading = false
  }

  import() {
    if (this.reloading) return
    this.reloading = true
    this.service.importAddress().subscribe({
      next: value => this.handleData(value),
      error: e => this.handleError(e)
    })

  }

  create() {

  }

  edit(a: Address) {

  }

  delete(a: Address) {

  }
}
