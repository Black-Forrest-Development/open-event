import {Component} from '@angular/core';
import {ShareService} from "../model/share.service";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {ShareInfo} from "../model/share-api";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-share-info',
  templateUrl: './share-info.component.html',
  styleUrl: './share-info.component.scss'
})
export class ShareInfoComponent {
  reloading: boolean = false
  info: ShareInfo | undefined

  constructor(private service: ShareService, private translate: TranslateService, private route: ActivatedRoute,) {
  }

  ngOnInit() {
    this.translate.setDefaultLang('en')
    this.reloading = true
    let lang = this.route.snapshot.queryParams['lang'] ?? 'en'
    this.translate.use(lang)
    this.route.paramMap.subscribe(p => this.handleParams(p))
  }


  private handleParams(p: ParamMap) {
    let idParam = p.get('id')
    if (!idParam) return
    this.service.getInfo(idParam).subscribe({
      next: value => this.handleData(value),
      error: err => this.handleError(err)
    })
  }

  private handleData(value: ShareInfo) {
    this.info = value
    this.reloading = false
  }

  private handleError(err: any) {
    this.reloading = false
  }
}
