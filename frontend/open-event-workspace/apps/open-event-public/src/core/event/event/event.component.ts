import {Component, computed, effect, resource, signal} from '@angular/core';
import {MatToolbar} from "@angular/material/toolbar";
import {ParticipantAddRequest, SharedParticipateResponse} from "@open-event-workspace/core";
import {ActivatedRoute, ParamMap, Params, RouterLink} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {MatDialog} from "@angular/material/dialog";
import {HotToastService} from "@ngxpert/hot-toast";
import {EventService, RegistrationService} from "@open-event-workspace/external";
import {LoadingBarComponent, toPromise} from "@open-event-workspace/shared";
import {EventInfoComponent} from "../event-info/event-info.component";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {ParticipateDialogComponent} from "../../registration/participate-dialog/participate-dialog.component";

@Component({
  selector: 'app-event',
  imports: [
    MatToolbar,
    RouterLink,
    LoadingBarComponent,
    EventInfoComponent
  ],
  templateUrl: './event.component.html',
  styleUrl: './event.component.scss'
})
export class EventComponent {


  eventId = signal<string | undefined>(undefined)
  eventResource = resource({
    request: this.eventId,
    loader: (param) => {
      return toPromise(this.service.getEvent(param.request))
    }
  })


  event = computed(this.eventResource.value ?? undefined)
  loading = this.eventResource.isLoading
  error = this.eventResource.error

  processing = signal(false)


  constructor(
    private service: EventService,
    private registrationService: RegistrationService,
    private translate: TranslateService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private hotToast: HotToastService,
  ) {
    effect(() => {
      this.handleError(this.error())
    });
    this.translate.setDefaultLang('en')
    this.route.queryParams.pipe(takeUntilDestroyed()).subscribe(p => this.handleQueryParams(p))
    this.route.paramMap.pipe(takeUntilDestroyed()).subscribe(p => this.handleParams(p))
  }

  private handleParams(p: ParamMap) {
    let idParam = p.get('id')
    if (!idParam) return
    this.eventId.set(idParam)
  }

  private handleQueryParams(p: Params) {
    let lang = p['lang']
    if (lang) this.translate.use(lang)
  }

  private handleError(err: any) {
    if (!err) return
    this.hotToast.error("Something went wrong")
  }

  participate() {
    if (this.processing()) return
    let dialogRef = this.dialog.open(ParticipateDialogComponent, {disableClose: true})
    dialogRef.afterClosed().subscribe(request => {
      if (request) this.requestParticipate(request)
    })
  }

  private requestParticipate(request: ParticipantAddRequest) {
    if (!this.isValid(request)) return
    let id = this.eventId()
    if (!id) return
    this.processing.set(true)
    this.registrationService.requestParticipation(id, request).subscribe({
      next: value => this.handleParticipateResponse(value),
      error: err => this.handleError(err)
    })
  }

  private isValid(request: ParticipantAddRequest) {
    if (request.size <= 0) return false
    return request.email.length > 0 || request.mobile.length > 0 || request.phone.length > 0
  }

  private handleParticipateResponse(response: SharedParticipateResponse) {
    // switch (response.status) {
    //   case 'ACCEPTED':
    //     this.translate.get('registration.message.accepted').subscribe(msg => this.hotToast.success(msg))
    //     this.participated = true
    //     break;
    //   case 'WAITING_LIST_DECREASE_SIZE':
    //   case 'WAITING_LIST':
    //     this.translate.get('registration.message.waiting').subscribe(msg => this.hotToast.info(msg))
    //     this.participated = true
    //     break;
    //   case 'DECLINED':
    //     this.translate.get('registration.message.declined').subscribe(msg => this.hotToast.warning(msg))
    //     break;
    //   case 'FAILED':
    //     this.translate.get('registration.message.failed').subscribe(msg => this.hotToast.error(msg))
    //     break;
    // }
    //
    // this.processing = false
    this.reload()
  }

  private reload() {
    this.eventResource.reload()
  }
}
