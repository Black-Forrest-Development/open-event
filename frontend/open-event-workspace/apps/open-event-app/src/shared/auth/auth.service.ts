import {effect, inject, Injectable} from '@angular/core';
import {KEYCLOAK_EVENT_SIGNAL, KeycloakEventType, ReadyArgs, typeEventArgs} from "keycloak-angular";
import {Principal} from "./principal";
import {environment} from "../../environments/environment";
import Keycloak from "keycloak-js";

// @deprecated due to deprecated keycloak service
@Injectable({
  providedIn: 'root'
})
export class AuthService {


  public static SETTINGS_READ = "openevent.settings.read"
  public static SETTINGS_WRITE = "openevent.settings.write"

  public static CATEGORY_READ = "openevent.category.read"
  public static CATEGORY_WRITE = "openevent.category.write"
  public static CATEGORY_ADMIN = "openevent.category.admin"

  public static REGISTRATION_READ = "openevent.registration.read"
  public static REGISTRATION_WRITE = "openevent.registration.write"
  public static REGISTRATION_MANAGE = "openevent.registration.manage"
  public static REGISTRATION_ADMIN = "openevent.registration.admin"

  public static CACHE_READ = "openevent.cache.read"
  public static CACHE_WRITE = "openevent.cache.write"

  public static EVENT_READ = "openevent.event.read"
  public static EVENT_WRITE = "openevent.event.write"
  public static EVENT_ADMIN = "openevent.event.admin"
  public static EVENT_MODERATOR = "openevent.event.mod"

  public static ACCOUNT_READ = "account.read"
  public static ACCOUNT_WRITE = "account.write"
  public static ACCOUNT_ADMIN = "openevent.account.admin"

  public static MAIL_READ = "openevent.mail.read"
  public static MAIL_WRITE = "openevent.mail.write"

  static BACKOFFICE_ACCESS = "openevent.backoffice.access";
  static PERMISSION_EXPORT = "openevent.export";

  public static HISTORY_READ = "openevent.history.read"
  public static HISTORY_ADMIN = "openevent.history.admin"

  public static PROFILE_READ = "openevent.profile.read"
  public static PROFILE_WRITE = "openevent.profile.write"
  public static PROFILE_ADMIN = "openevent.profile.admin"

  public static ADDRESS_READ = "address.read"

  public static ACTIVITY_READ = "activity.read"

  private principal: Principal | undefined
  private authenticated = false

  constructor(private readonly keycloak: Keycloak) {
    const keycloakSignal = inject(KEYCLOAK_EVENT_SIGNAL)

    effect(() => {
      const keycloakEvent = keycloakSignal();

      if (keycloakEvent.type === KeycloakEventType.Ready) {
        this.authenticated = typeEventArgs<ReadyArgs>(keycloakEvent.args)
        let token = this.keycloak.tokenParsed
        if(token){
          this.setPrincipal(token)
        } else {
          this.clearPrincipal()
        }
      }

      if (keycloakEvent.type === KeycloakEventType.AuthLogout) {
        this.authenticated = false
        this.clearPrincipal()
      }
    });
  }


  public logout() {
    this.keycloak.logout({redirectUri: environment.logoutUrl}).then()
  }

  public getPrincipal(): Principal | undefined {
    return this.principal
  }

  hasRole(...roles: String[]): boolean {
    if (!this.principal) return false
    return this.principal.roles.find(r => roles.find(p => r === p)) != null
  }

  private clearPrincipal() {
    console.log('Clear principal');
    this.principal = undefined;
  }

  private setPrincipal(token: any) {
    // console.info(JSON.stringify(token));
    const id = token["sub"];
    const email = token["email"];
    const username = token["preferred_username"];
    const given_name = token["given_name"];
    const family_name = token["family_name"];
    const roles = token["realm_access"]["roles"];

    this.principal = new Principal(id, email, username, given_name, family_name, roles);
    // console.log('Set principal to ' + JSON.stringify(this.principal));
  }


  getRoles(): string[] {
    if (!this.principal) return []
    return this.principal.roles.sort();
  }
}
