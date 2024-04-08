import {Component} from '@angular/core';
import {Profile, ProfileChangeRequest} from "../model/profile-api";
import {ProfileService} from "../model/profile.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from "@angular/material/form-field";

@Component({
  selector: 'app-profile-change',
  templateUrl: './profile-change.component.html',
  styleUrl: './profile-change.component.scss',
  providers: [{provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'outline'}}]
})
export class ProfileChangeComponent {
  profile: Profile | undefined
  reloading: boolean = false
  editMode: boolean = false

  fg: FormGroup

  constructor(private fb: FormBuilder, private service: ProfileService) {
    this.fg = this.fb.group({
      email: this.fb.control('', Validators.email),
      phone: this.fb.control(''),
      mobile: this.fb.control(''),
      firstName: this.fb.control('', Validators.required),
      lastName: this.fb.control('', Validators.required),
      dateOfBirth: this.fb.control(''),
      gender: this.fb.control(''),
      profilePicture: this.fb.control(''),
      website: this.fb.control(''),

    })
  }

  ngOnInit() {
    this.reload()
  }

  save() {
    if (this.fg.dirty && this.profile) {
      let request = this.fg.value as ProfileChangeRequest
      this.reloading = true
      this.service.updateProfile(this.profile.id, request).subscribe(d => this.handleData(d))
    }
    this.editMode = false
  }

  private reload() {
    if (this.reloading) return
    this.reloading = true
    this.service.getOwnProfile().subscribe(d => this.handleData(d))
  }

  private handleData(d: Profile) {
    this.profile = d
    this.fg.setValue({
      email: d.email,
      phone: d.phone,
      mobile: d.mobile,
      firstName: d.firstName,
      lastName: d.lastName,
      dateOfBirth: d.dateOfBirth,
      gender: d.gender,
      profilePicture: d.profilePicture,
      website: d.website
    })
    this.reloading = false
  }
}
