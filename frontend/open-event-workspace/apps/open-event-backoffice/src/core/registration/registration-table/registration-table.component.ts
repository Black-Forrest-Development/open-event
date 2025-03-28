import {Component, computed, effect, input, resource} from '@angular/core';
import {RegistrationService} from '@open-event-workspace/backoffice';
import {Participant, ParticipantDetails, ParticipateRequest, ParticipateResponse, RegistrationInfo} from "@open-event-workspace/core";
import {AuthService, toPromise} from "@open-event-workspace/shared";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSortModule} from "@angular/material/sort";
import {TranslatePipe, TranslateService} from "@ngx-translate/core";
import {DatePipe} from "@angular/common";
import {MatDialog} from "@angular/material/dialog";
import {HotToastService} from "@ngxpert/hot-toast";
import {RegistrationParticipantEditDialogComponent} from "../registration-participant-edit-dialog/registration-participant-edit-dialog.component";
import {RegistrationParticipantRemoveDialogComponent} from "../registration-participant-remove-dialog/registration-participant-remove-dialog.component";

@Component({
  selector: 'app-registration-table',
  imports: [MatTableModule, MatButtonModule, MatIconModule, MatPaginatorModule, MatSortModule, TranslatePipe, DatePipe],
  templateUrl: './registration-table.component.html',
  styleUrl: './registration-table.component.scss'
})
export class RegistrationTableComponent {

  data = input.required<RegistrationInfo>()

  registrationResource = resource({
    request: this.data,
    loader: (param) => {
      return toPromise(this.service.getRegistrationDetails(param.request.registration.id))
    }
  })

  registration = computed(this.registrationResource.value ?? undefined)
  loading = this.registrationResource.isLoading
  error = this.registrationResource.error

  displayedColumns: string[] = ['rank', 'size', 'status', 'waitinglist', 'name', 'email', 'phone', 'mobile', 'timestamp', 'action']
  dataSource = new MatTableDataSource<ParticipantDetails>([])

  constructor(
    private service: RegistrationService,
    private dialog: MatDialog,
    private hotToast: HotToastService,
    private translation: TranslateService,
    private authService: AuthService
  ) {
    effect(() => {
      this.dataSource.data = this.registration()?.participants ?? []
    });
  }

  editParticipant(part: Participant) {
    let dialogRef = this.dialog.open(RegistrationParticipantEditDialogComponent, {data: part})
    dialogRef.afterClosed().subscribe(request => {
      if (request) this.requestEditParticipant(part, request)
    })
  }

  removeParticipant(part: Participant) {
    let dialogRef = this.dialog.open(RegistrationParticipantRemoveDialogComponent, {data: part})
    dialogRef.afterClosed().subscribe(request => {
      if (request) this.requestRemoveParticipant(part)
    })
  }

  private requestEditParticipant(participant: Participant, request: ParticipateRequest) {
    this.reloading = true
    this.service.changeParticipant(this.data().registration.id, participant.id, request).subscribe(r => this.handleParticipateResponse(r))
  }

  private requestRemoveParticipant(participant: Participant) {
    this.reloading = true
    this.service.removeParticipant(this.data().registration.id, participant.id).subscribe(r => this.handleParticipateResponse(r))
  }

  private handleParticipateResponse(response: ParticipateResponse) {
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
    this.reloadDetails()
  }
}
