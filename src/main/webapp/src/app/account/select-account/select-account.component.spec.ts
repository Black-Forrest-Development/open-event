import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SelectAccountComponent} from './select-account.component';

describe('SelectAccountComponent', () => {
  let component: SelectAccountComponent;
  let fixture: ComponentFixture<SelectAccountComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SelectAccountComponent]
    });
    fixture = TestBed.createComponent(SelectAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
