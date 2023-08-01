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
