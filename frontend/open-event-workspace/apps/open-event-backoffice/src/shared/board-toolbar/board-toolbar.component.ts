import {Component, Directive, input, output} from '@angular/core';
import {MatToolbar} from "@angular/material/toolbar";
import {TranslatePipe} from "@ngx-translate/core";
import {BoardToolbarSearchComponent} from "../board-toolbar-search/board-toolbar-search.component";
import {MatButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";

@Component({
  imports: [
    MatButton,
    MatIcon,
    MatToolbar,
    TranslatePipe,
    BoardToolbarSearchComponent
  ],
  selector: 'app-board-toolbar',
  styleUrl: './board-toolbar.component.scss',
  templateUrl: './board-toolbar.component.html'
})
export class BoardToolbarComponent {
  title = input('')
  searchPlaceholder = input('')
  showReload = input(true)
  showSearch = input(true)


  fullTextSearch = output<string>()
  reload = output<boolean>()

}

@Directive({
  selector: `toolbar-actions, [toolbar-actions], [toolbarActions]`,
})
export class ToolbarActions {
}
