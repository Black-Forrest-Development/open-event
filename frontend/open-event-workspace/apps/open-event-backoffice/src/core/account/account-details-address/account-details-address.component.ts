import {Component, computed, input, resource, signal} from '@angular/core';
import {Account, Address} from "@open-event-workspace/core";
import {MatDivider} from "@angular/material/divider";
import {TranslatePipe} from "@ngx-translate/core";
import {LoadingBarComponent, toPromise} from "@open-event-workspace/shared";
import {AccountService} from "@open-event-workspace/backoffice";
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule, PageEvent} from "@angular/material/paginator";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";

@Component({
  selector: 'app-account-details-address',
  imports: [
    MatDivider,
    MatTableModule,
    MatPaginatorModule,
    MatIconModule,
    MatButtonModule,
    TranslatePipe,
    LoadingBarComponent
  ],
  templateUrl: './account-details-address.component.html',
  styleUrl: './account-details-address.component.scss'
})
export class AccountDetailsAddressComponent {
  data = input.required<Account>()

  page = signal(0)
  size = signal(20)

  addressCriteria = computed(() => ({
    data: this.data(),
    page: this.page(),
    size: this.size(),
  }));

  addressResource = resource({
    request: this.addressCriteria,
    loader: (param) => {
      return toPromise(this.service.getAddress(param.request.data.id, param.request.page, param.request.size))
    }
  })

  result = computed(this.addressResource.value ?? undefined)

  address = computed(() => this.result()?.content ?? [])
  totalSize = computed(() => this.result()?.totalSize ?? 0)
  loading = this.addressResource.isLoading
  error = this.addressResource.error

  displayedColumns: string[] = ['street', 'streetNumber', 'zip', 'city', 'country', 'additionalInfo', 'cmd']

  constructor(private service: AccountService) {
  }

  handlePageChange($event: PageEvent) {
    this.page.set($event.pageIndex)
    this.size.set($event.pageSize)
  }

  editAddress(address: Address) {

  }

  deleteAddress(address: Address) {

  }
}
