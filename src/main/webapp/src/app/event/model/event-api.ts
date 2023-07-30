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


export interface Account {
  id: number,
  name: string,
  firstName: string,
  lastName: string,
  email: string,
  iconUrl: string,
}
