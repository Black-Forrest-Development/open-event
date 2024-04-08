import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ProfileBoardComponent} from './profile-board.component';

describe('ProfileBoardComponent', () => {
  let component: ProfileBoardComponent;
  let fixture: ComponentFixture<ProfileBoardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfileBoardComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ProfileBoardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
