import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EventAdminShareComponent} from './event-admin-share.component';

describe('EventAdminShareComponent', () => {
  let component: EventAdminShareComponent;
  let fixture: ComponentFixture<EventAdminShareComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EventAdminShareComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EventAdminShareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
