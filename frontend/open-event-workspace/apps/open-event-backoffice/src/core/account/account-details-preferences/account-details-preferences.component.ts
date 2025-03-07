import {Component, computed, input, resource} from '@angular/core';
import {Account} from "@open-event-workspace/core";
import {MatDivider} from "@angular/material/divider";
import {TranslatePipe} from "@ngx-translate/core";
import {LoadingBarComponent, toPromise} from "@open-event-workspace/shared";
import {AccountService} from "@open-event-workspace/backoffice";

@Component({
  selector: 'app-account-details-preferences',
  imports: [
    MatDivider,
    TranslatePipe,
    LoadingBarComponent
  ],
  templateUrl: './account-details-preferences.component.html',
  styleUrl: './account-details-preferences.component.scss'
})
export class AccountDetailsPreferencesComponent {
  data = input.required<Account>()

  preferencesResource = resource({
    request: this.data,
    loader: (param) => {
      return toPromise(this.service.getPreferences(param.request.id))
    }
  })

  preferences = computed(this.preferencesResource.value ?? undefined)
  loading = this.preferencesResource.isLoading
  error = this.preferencesResource.error

  constructor(private service: AccountService) {
  }

}
