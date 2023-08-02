import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EventBoardComponent} from "./event-board/event-board.component";
import {EventChangeComponent} from "./event-change/event-change.component";
import {EventDetailsComponent} from "./event-details/event-details.component";

const routes: Routes = [
  {path: '', component: EventBoardComponent},
  {path: 'create', component: EventChangeComponent},
  {path: 'details/:id', component: EventDetailsComponent},
  {path: 'edit/:id', component: EventChangeComponent},
  {path: 'copy/:id', component: EventChangeComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EventRoutingModule {
}
