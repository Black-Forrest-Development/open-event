import {Routes} from "@angular/router";
import {ShareInfoComponent} from "./share-info/share-info.component";

export const routes: Routes = [
  {path: 'info/:id', component: ShareInfoComponent},
];
