import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./category-board/category-board.component').then(m => m.CategoryBoardComponent),
  }
]
