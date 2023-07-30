import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EventBoardComponent} from "./event-board/event-board.component";

const routes: Routes = [
  {path: '', component: EventBoardComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EventRoutingModule {
}
