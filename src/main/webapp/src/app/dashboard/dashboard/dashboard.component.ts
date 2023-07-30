import {Component, ViewChild} from '@angular/core';
import {AuthService} from "../../auth/auth.service";
import {MatSelectChange} from "@angular/material/select";
import {TranslateService} from "@ngx-translate/core";
import {NavigationEnd, Router} from "@angular/router";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";
import {filter, map, Observable, withLatestFrom} from "rxjs";
import {MatSidenav} from "@angular/material/sidenav";

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

    isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
        .pipe(map(result => result.matches));


    lang: string = 'de';
    @ViewChild('drawer') drawer: MatSidenav | undefined;

    constructor(public authService: AuthService, private translate: TranslateService, router: Router, private breakpointObserver: BreakpointObserver,) {
        translate.setDefaultLang('en')
        translate.use(this.lang)

        router.events.pipe(
            withLatestFrom(this.isHandset$),
            filter(([a, b]) => b && a instanceof NavigationEnd)
        ).subscribe(_ => this.drawer?.close())
    }

    changeLang(event: MatSelectChange) {
        let value = event.value;
        this.translate.use(value)
    }

    selectLang(value: string) {
        this.lang = value;
        this.translate.use(this.lang)
    }

}
