import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./mail-board/mail-board.component').then(m => m.MailBoardComponent),
  },
  {
    path: 'history/:id',
    loadComponent: () => import('./mail-history/mail-history.component').then(m => m.MailHistoryComponent),
  },
];
