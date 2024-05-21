import {AccountInfo} from "../../account/model/account-api";

export interface Share {
  id: string,
  eventId: number,
  published: boolean,

  owner: AccountInfo,
  created: string,
  changed: string | null
}
