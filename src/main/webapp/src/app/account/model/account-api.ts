export interface Account {
  id: number,
  externalId: string | undefined,
  name: string,
  iconUrl: string,

  registrationDate: string,
  lastLoginDate: string | undefined,

  serviceAccount: boolean,
  idpLinked: boolean,
}

export interface AccountInfo {
   id: number,
   name: string,
   iconUrl: string,
   email: string,
   firstName: string,
   lastName: string,
}

export interface AccountValidationResult {
  created: boolean,
  account: Account
}

export class AccountChangeRequest {
  constructor(
    public name: string,
    public iconUrl: string,
    public externalId: string | undefined
  ) {
  }
}


