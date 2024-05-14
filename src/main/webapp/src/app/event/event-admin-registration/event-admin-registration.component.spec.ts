import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EventAdminRegistrationComponent} from './event-admin-registration.component';

describe('EventAdminRegistrationComponent', () => {
  let component: EventAdminRegistrationComponent;
  let fixture: ComponentFixture<EventAdminRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EventAdminRegistrationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EventAdminRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
