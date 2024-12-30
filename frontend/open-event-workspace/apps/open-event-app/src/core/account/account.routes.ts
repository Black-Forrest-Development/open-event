import {Routes} from "@angular/router";
import {AccountBoardComponent} from "./account-board/account-board.component";
import {AccountProfileComponent} from "./account-profile/account-profile.component";
import {AccountAddressComponent} from "./account-address/account-address.component";
import {AccountPreferencesComponent} from "./account-preferences/account-preferences.component";
import {AccountActivityComponent} from "./account-activity/account-activity.component";

export const routes: Routes = [
  {
    path: '',
    component: AccountBoardComponent,
    children: [
      {path: '', redirectTo: 'profile', pathMatch: "full"},
      {path: 'profile', component: AccountProfileComponent},
      {path: 'address', component: AccountAddressComponent},
      {path: 'preferences', component: AccountPreferencesComponent},
      {path: 'activity', component: AccountActivityComponent},
    ]
  },
];
