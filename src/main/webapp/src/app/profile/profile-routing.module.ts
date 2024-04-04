import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProfileBoardComponent} from "./profile-board/profile-board.component";

const routes: Routes = [
  {path: '', component: ProfileBoardComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule {
}
