import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./account-board/account-board.component').then(m => m.AccountBoardComponent),
    children: [
      {path: '', redirectTo: 'profile', pathMatch: "full"},
      {
        path: 'profile',
        loadComponent: () => import('./account-profile/account-profile.component').then(m => m.AccountProfileComponent)
      },
      {
        path: 'address',
        loadComponent: () => import('./account-address/account-address.component').then(m => m.AccountAddressComponent)
      },
      {
        path: 'preferences',
        loadComponent: () => import('./account-preferences/account-preferences.component').then(m => m.AccountPreferencesComponent)
      },
      {
        path: 'activity',
        loadComponent: () => import('./account-activity/account-activity.component').then(m => m.AccountActivityComponent)
      },
    ]
  },
];
