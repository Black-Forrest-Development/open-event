import {Routes} from "@angular/router";
import {MailBoardComponent} from "./mail-board/mail-board.component";
import {MailHistoryComponent} from "./mail-history/mail-history.component";

export const routes: Routes = [
  {path: '', component: MailBoardComponent},
  {path: 'history/:id', component: MailHistoryComponent},
];
