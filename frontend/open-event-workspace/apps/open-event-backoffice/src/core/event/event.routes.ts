import {Routes} from "@angular/router";

export const routes: Routes = [
  {path: '', loadComponent: () => import('./event.component').then(m => m.EventComponent)},
];
