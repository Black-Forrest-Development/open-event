import {Injectable} from '@angular/core';
import {Title} from "@angular/platform-browser";
import {SettingService} from "@open-event-workspace/core";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  title: string = 'app.title'

  constructor(settingsService: SettingService, private pageTitle: Title) {

    settingsService.getTitle().subscribe(d => {
      this.pageTitle.setTitle(d.text)
      this.title = d.text
    })

  }


}
