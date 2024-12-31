import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./settings-board/settings-board.component').then(m => m.SettingsBoardComponent),
  }
]
