import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ParticipateDialogComponent} from './participate-dialog.component';

describe('ParticipateDialogComponent', () => {
  let component: ParticipateDialogComponent;
  let fixture: ComponentFixture<ParticipateDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ParticipateDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ParticipateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
