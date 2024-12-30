import {Component, Input} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {HotToastService} from "@ngxpert/hot-toast";
import {
  RegistrationParticipateDialogComponent
} from "../registration-participate-dialog/registration-participate-dialog.component";
import {TranslatePipe, TranslateService} from "@ngx-translate/core";
import {RegistrationEditDialogComponent} from "../registration-edit-dialog/registration-edit-dialog.component";
import {RegistrationCancelDialogComponent} from "../registration-cancel-dialog/registration-cancel-dialog.component";
import {
  RegistrationParticipateAccountDialogComponent
} from "../registration-participate-account-dialog/registration-participate-account-dialog.component";
import {
  RegistrationParticipateManualDialogComponent
} from "../registration-participate-manual-dialog/registration-participate-manual-dialog.component";
import {AuthService} from "../../../shared/auth/auth.service";
import {
  Participant,
  ParticipantAddRequest,
  ParticipateRequest,
  ParticipateResponse,
  RegistrationInfo,
  RegistrationService
} from '@open-event-workspace/core';
import {MatButton} from "@angular/material/button";
import {NgForOf, NgIf, NgTemplateOutlet} from "@angular/common";
import {MatIcon} from "@angular/material/icon";
import {MatProgressBar} from "@angular/material/progress-bar";
import {MatCard, MatCardActions, MatCardContent, MatCardHeader} from "@angular/material/card";
import {RegistrationStatusComponent} from "../registration-status/registration-status.component";
import {MatDivider} from "@angular/material/divider";
import {AccountComponent} from "../../account/account/account.component";

@Component({
  selector: 'app-registration-details',
  templateUrl: './registration-details.component.html',
  styleUrls: ['./registration-details.component.scss'],
  imports: [
    NgIf,
    MatIcon,
    TranslatePipe,
    MatProgressBar,
    MatCard,
    MatCardHeader,
    RegistrationStatusComponent,
    MatDivider,
    MatCardContent,
    NgTemplateOutlet,
    NgForOf,
    MatButton,
    MatCardActions,
    AccountComponent
  ],
  standalone: true
})
export class RegistrationDetailsComponent {
  registration: RegistrationInfo | undefined
  accepted: Participant[] = []
  waitList: Participant[] = []
  reloading: boolean = false
  userParticipant: Participant | undefined
  adminOrManager: boolean = false

  constructor(
    private service: RegistrationService,
    private dialog: MatDialog,
    private hotToast: HotToastService,
    private translation: TranslateService,
    private authService: AuthService
  ) {
  }

  @Input()
  set data(value: RegistrationInfo) {
    this.registration = value
    this.updateData()
  }

  ngOnInit() {
    this.adminOrManager = this.authService.hasRole(AuthService.REGISTRATION_MANAGE, AuthService.REGISTRATION_ADMIN)
    let principal = this.authService.getPrincipal()
    if (principal) this.adminOrManager = principal.roles.find(r => r === 'openevent.registration.manage' || r === 'openevent.registration.admin') != null
  }

  participateSelf() {
    if (!this.registration) return
    if (this.reloading) return
    let dialogRef = this.dialog.open(RegistrationParticipateDialogComponent)
    dialogRef.afterClosed().subscribe(request => {
      if (request) this.requestParticipateSelf(request)
    })
  }

  editSelf() {
    if (!this.registration) return
    if (this.reloading) return

    let dialogRef = this.dialog.open(RegistrationEditDialogComponent, {data: this.userParticipant})
    dialogRef.afterClosed().subscribe(request => {
      if (request) this.requestEditSelf(request)
    })
  }

  cancelSelf() {
    if (!this.registration) return
    if (this.reloading) return

    let dialogRef = this.dialog.open(RegistrationCancelDialogComponent)
    dialogRef.afterClosed().subscribe(request => {
      if (request) this.requestCancelSelf()
    })
  }

  participateAccount() {
    if (!this.registration) return
    if (this.reloading) return
    let dialogRef = this.dialog.open(RegistrationParticipateAccountDialogComponent)
    dialogRef.afterClosed().subscribe(request => {
      if (request) this.requestParticipateAccount(request)
    })
  }

  participateManual() {
    if (!this.registration) return
    if (this.reloading) return
    let dialogRef = this.dialog.open(RegistrationParticipateManualDialogComponent)
    dialogRef.afterClosed().subscribe(request => {
      if (request) this.requestParticipateManual(request)
    })
  }

  editParticipant(part: Participant) {
    if (!this.registration) return
    if (this.reloading) return

    let dialogRef = this.dialog.open(RegistrationEditDialogComponent, {data: part})
    dialogRef.afterClosed().subscribe(request => {
      if (request) this.requestEditParticipant(part, request)
    })
  }

  removeParticipant(part: Participant) {
    if (!this.registration) return
    if (this.reloading) return

    let dialogRef = this.dialog.open(RegistrationCancelDialogComponent, {data: part})
    dialogRef.afterClosed().subscribe(request => {
      if (request) this.requestRemoveParticipant(part)
    })
  }

  private updateData() {
    if (this.registration) {
      this.accepted = this.registration.participants.filter(p => !p.waitingList)
      this.waitList = this.registration.participants.filter(p => p.waitingList)
      this.userParticipant = this.registration.participants.find(p => p.author.email === this.authService.getPrincipal()?.email)
    }
  }

  private requestParticipateSelf(request: ParticipateRequest) {
    if (!this.registration) return
    if (this.reloading) return
    this.reloading = true
    this.service.participateSelf(this.registration.registration.id, request).subscribe(r => this.handleParticipateResponse(r))
  }

  private requestEditSelf(request: ParticipateRequest) {
    if (!this.registration) return
    if (this.reloading) return
    this.reloading = true
    this.service.editSelf(this.registration.registration.id, request).subscribe(r => this.handleParticipateResponse(r))
  }

  private requestCancelSelf() {
    if (!this.registration) return
    if (this.reloading) return
    this.reloading = true
    this.service.cancelSelf(this.registration.registration.id).subscribe(r => this.handleParticipateResponse(r))
  }

  private requestParticipateAccount(request: any) {

  }

  private requestParticipateManual(request: ParticipantAddRequest) {
    if (!this.registration) return
    if (this.reloading) return
    this.reloading = true
    this.service.participateManual(this.registration.registration.id, request).subscribe(r => this.handleParticipateResponse(r))
  }

  private handleParticipateResponse(response: ParticipateResponse) {
    this.updateParticipants(response.participants)
    switch (response.status) {
      case 'ACCEPTED':
        this.translation.get('registration.message.accepted').subscribe(msg => this.hotToast.success(msg))
        break;
      case 'WAITING_LIST_DECREASE_SIZE':
      case 'WAITING_LIST':
        this.translation.get('registration.message.waiting').subscribe(msg => this.hotToast.info(msg))
        break;
      case 'DECLINED':
        this.translation.get('registration.message.declined').subscribe(msg => this.hotToast.warning(msg))
        break;
      case 'FAILED':
        this.translation.get('registration.message.failed').subscribe(msg => this.hotToast.error(msg))
        break;
    }
    this.reloading = false
  }

  private requestEditParticipant(participant: Participant, request: ParticipateRequest) {
    if (!this.registration) return
    if (this.reloading) return
    this.reloading = true
    this.service.changeParticipant(this.registration.registration.id, participant.id, request).subscribe(r => this.handleParticipateResponse(r))
  }

  private requestRemoveParticipant(participant: Participant) {
    if (!this.registration) return
    if (this.reloading) return
    this.reloading = true
    this.service.removeParticipant(this.registration.registration.id, participant.id).subscribe(r => this.handleParticipateResponse(r))
  }

  private updateParticipants(participants: Participant[]) {
    if (!this.registration) return
    this.registration.participants = participants
    this.updateData()
  }
}
