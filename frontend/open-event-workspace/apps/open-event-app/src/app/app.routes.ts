import {Route} from '@angular/router';
import {PageNotFoundComponent} from "../shared/page-not-found/page-not-found.component";
import {AuthService} from "../shared/auth/auth.service";
import {canActivateAuthRole} from "../shared/auth/auth.guard";
import {ForbiddenComponent} from "../shared/forbidden/forbidden.component";

export const appRoutes: Route[] = [
  {path: '', pathMatch: 'full', redirectTo: 'event'},
  {
    path: 'event',
    loadChildren: () => import('../core/event/event.routes').then(m => m.routes),
    // canActivate: [canActivateAuthRole],
  },
  {
    path: 'category',
    loadChildren: () => import('../core/category/category.routes').then(m => m.routes),
    canActivate: [canActivateAuthRole],
    data: {roles: [AuthService.CATEGORY_READ, AuthService.CATEGORY_WRITE]}
  },
  {
    path: 'settings',
    loadChildren: () => import('../core/settings/settings.routes').then(m => m.routes),
    canActivate: [canActivateAuthRole],
    data: {roles: [AuthService.SETTINGS_READ, AuthService.SETTINGS_WRITE]}
  },
  {
    path: 'cache',
    loadChildren: () => import('../core/cache/cache.routes').then(m => m.routes),
    canActivate: [canActivateAuthRole],
    data: {roles: [AuthService.CACHE_READ, AuthService.CACHE_WRITE]}
  },
  {
    path: 'mail',
    loadChildren: () => import('../core/mail/mail.routes').then(m => m.routes),
    canActivate: [canActivateAuthRole],
    data: {roles: [AuthService.MAIL_READ, AuthService.MAIL_WRITE]}
  },
  // {
  //   path: 'backoffice',
  //   loadChildren: () => import('../core/backoffice/backoffice.routes').then(m => m.routes),
  //   canActivate: [canActivateAuthRole],
  //   data: {roles: [AuthService.BACKOFFICE_ACCESS]}
  // },
  {
    path: 'history',
    loadChildren: () => import('../core/history/history.routes').then(m => m.routes),
    canActivate: [canActivateAuthRole],
    data: {roles: [AuthService.HISTORY_ADMIN]}
  },
  {
    path: 'account',
    loadChildren: () => import('../core/account/account.routes').then(m => m.routes),
    canActivate: [canActivateAuthRole],
    data: {roles: [AuthService.ACCOUNT_READ]}
  },
  {
    path: 'share',
    loadChildren: () => import('../core/share/share.routes').then(m => m.routes),
    data: {public: true}
  },
  {path: 'forbidden', component: ForbiddenComponent},
  {path: '**', component: PageNotFoundComponent}
]
