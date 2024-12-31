import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./cache-board/cache-board.component').then(m => m.CacheBoardComponent),
  }
]
