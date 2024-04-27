import {Page} from "../../shared/model/page";
import {AccountInfo} from "../../account/model/account-api";

export class EventSearchRequest {
  public constructor(
    fullTextSearch: string,
    from: string,
    to: string,
    ownEvents: boolean,
    participatingEvents: boolean
  ) {
  }
}


export interface EventSearchResponse {
  result: Page<EventSearchEntry>
}


export interface EventSearchEntry {
  // event data
  id: string,
  start: string,
  finish: string,

  title: string,
  shortText: string,
  longText: string,
  published: boolean,

  owner: AccountInfo,

  // location data
  street: string,
  streetNumber: string,
  zip: string,
  city: string,
  country: string,

  lat: number,
  lon: number,

  // registration data
  hasSpaceLeft: boolean,
  maxGuestAmount: number,
  amountAccepted: number,
  amountOnWaitingList: number,
  remainingSpace: number,

  // user specific flags
  ownEvent: boolean,
  participatingEvent: boolean,

  // categories
  categories: string[],
}
