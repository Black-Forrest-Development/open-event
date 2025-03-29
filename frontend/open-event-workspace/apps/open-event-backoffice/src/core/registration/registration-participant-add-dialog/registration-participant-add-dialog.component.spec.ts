import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RegistrationParticipantAddDialogComponent} from './registration-participant-add-dialog.component';

describe('RegistrationParticipantAddDialogComponent', () => {
  let component: RegistrationParticipantAddDialogComponent;
  let fixture: ComponentFixture<RegistrationParticipantAddDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegistrationParticipantAddDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistrationParticipantAddDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
