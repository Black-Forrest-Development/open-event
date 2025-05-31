import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: ':id',
    loadComponent: () => import('./event/event.component').then(m => m.EventComponent),
    data: {action: ''}
  },
  {
    path: ':id/confirm',
    loadComponent: () => import('./event/event.component').then(m => m.EventComponent),
    data: {action: 'confirm'}
  }
];
