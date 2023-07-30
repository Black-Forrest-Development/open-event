import {Injectable} from '@angular/core';
import {KeycloakService} from "keycloak-angular";
import {Principal} from "./principal";
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public static ADMIN = 'ADMINISTRATOR';
  public static EDITOR = 'EDITOR';
  public static MANAGER = 'MANAGER';
  public static USER = 'USER';

  private principal: Principal | undefined;

  constructor(private keycloak: KeycloakService, private http: HttpClient) {
    try {
      let token = this.keycloak.getKeycloakInstance().tokenParsed;
      if (token == null) {
        this.clearPrincipal();
      } else {
        this.setPrincipal(token);
      }
    } catch (e) {
      console.log('Failed to load user details', e);
      this.clearPrincipal();
    }
  }

  public isAdministrator(): boolean {
    return this.hasRole(AuthService.ADMIN)
  }

  public isManager(): boolean {
    return this.hasRole(AuthService.MANAGER)
  }

  public isEditor(): boolean {
    return this.hasRole(AuthService.EDITOR)
  }

  public hasRole(role: string): boolean {
    if (this.principal == null) return false;
    if (role == AuthService.USER) return true;

    return this.principal.roles.find(r => r == role) != null;
  }

  public logout() {
    this.keycloak.logout("/").then()
  }

  public isLoggedIn(): boolean {
    return this.principal != null
  }


  public getPrincipal(): Principal | undefined {
    return this.principal
  }

  private clearPrincipal() {
    console.log('Clear principal');
    this.principal = undefined;
  }

  private setPrincipal(token: any) {
    console.info(JSON.stringify(token));
    const id = token["sub"];
    const email = token["email"];
    const username = token["preferred_username"];
    const given_name = token["given_name"];
    const family_name = token["family_name"];
    const roles = token["resource_access"]["open-church-backend"]["roles"];

    this.principal = new Principal(id, email, username, given_name, family_name, roles);
    console.log('Set principal to ' + JSON.stringify(this.principal));
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

  getToken() {
    return this.keycloak.getKeycloakInstance().token
  }

}
