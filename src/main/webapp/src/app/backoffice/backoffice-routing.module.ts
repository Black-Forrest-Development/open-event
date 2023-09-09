import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {BackofficeBoardComponent} from "./backoffice-board/backoffice-board.component";

const routes: Routes = [
  {path: '', component: BackofficeBoardComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BackofficeRoutingModule {
}
