import {Account} from "../../account/model/account-api";

export interface Event {
  id: number,
  owner: Account,
  start: string,
  finish: string,

  title: string,
  shortText: string,
  longText: string,
  imageUrl: string,
  iconUrl: string,

  hasLocation: boolean,
  hasRegistration: boolean,
  published: boolean,
}


export class EventChangeRequest {
  constructor(
    public start: string,
    public finish: string,
    public title: string,
    public shortText: string,
    public longText: string,
    public imageUrl: string,
    public iconUrl: string,
    public location: LocationChangeRequest,
    public registration: RegistrationChangeRequest
  ) {
  }
}

export class LocationChangeRequest {
  constructor(
    public street: string,
    public streetNumber: string,
    public zip: string,
    public city: string,
    public country: string,
    public additionalInfo: string,
    public lat: number,
    public lon: number,
    public size: number
  ) {
  }
}

export class RegistrationChangeRequest {
  constructor(
    public maxGuestAmount: number,
    public interestedAllowed: boolean,
    public ticketsEnabled: boolean
  ) {
  }
}

export class PatchRequest<T> {
  constructor(
    public value: T
  ) {

  }

}
