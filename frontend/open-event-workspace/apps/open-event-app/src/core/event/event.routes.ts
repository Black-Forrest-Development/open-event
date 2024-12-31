import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./event-board/event-board.component').then(m => m.EventBoardComponent),
  },
  {
    path: 'create',
    loadComponent: () => import('./event-change/event-change.component').then(m => m.EventChangeComponent),
  },
  {
    path: 'details/:id',
    loadComponent: () => import('./event-details/event-details.component').then(m => m.EventDetailsComponent),
  },
  {
    path: 'edit/:id',
    loadComponent: () => import('./event-change/event-change.component').then(m => m.EventChangeComponent),
  },
  {
    path: 'copy/:id',
    loadComponent: () => import('./event-change/event-change.component').then(m => m.EventChangeComponent),
  },
  {
    path: 'admin/:id',
    loadComponent: () => import('./event-admin/event-admin.component').then(m => m.EventAdminComponent),
  },
];
