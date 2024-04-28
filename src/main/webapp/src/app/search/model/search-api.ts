import {Page} from "../../shared/model/page";
import {AccountInfo} from "../../account/model/account-api";

export class EventSearchRequest {
  public constructor(
    public fullTextSearch: string,
    public from: string | undefined,
    public to: string | undefined,
    public ownEvents: boolean,
    public participatingEvents: boolean
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
  hasLocation: boolean,
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
