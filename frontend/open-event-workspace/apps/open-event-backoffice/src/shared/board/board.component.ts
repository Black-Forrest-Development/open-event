import {Component, Directive, input, output} from '@angular/core';
import {LoadingBarComponent} from "@open-event-workspace/shared";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {BoardToolbarComponent, ToolbarActions} from "../board-toolbar/board-toolbar.component";

@Component({
  selector: 'app-board',
  imports: [
    LoadingBarComponent,
    MatButtonModule,
    MatIconModule,
    BoardToolbarComponent,
    ToolbarActions
  ],
  templateUrl: './board.component.html',
  styleUrl: './board.component.scss'
})
export class BoardComponent {
  reloading = input(false)
  title = input('')
  searchPlaceholder = input('')
  showReload = input(true)
  showSearch = input(true)

  fullTextSearch = output<string>()
  reload = output<boolean>()
}

@Directive({
  selector: `board-toolbar-actions, [board-toolbar-actions], [boardToolbarActions]`,
})
export class BoardToolbarActions {
}
