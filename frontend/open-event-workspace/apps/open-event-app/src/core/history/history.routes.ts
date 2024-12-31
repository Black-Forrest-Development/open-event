import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./history-board/history-board.component').then(m => m.HistoryBoardComponent),
  },
];
