import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AddressChangeDialogComponent} from './address-change-dialog.component';

describe('AddressChangeDialogComponent', () => {
  let component: AddressChangeDialogComponent;
  let fixture: ComponentFixture<AddressChangeDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddressChangeDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddressChangeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
