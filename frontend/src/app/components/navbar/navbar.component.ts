import { Component } from '@angular/core';
import {KeycloakProfile} from 'keycloak-js';
import {KeycloakService} from 'keycloak-angular';

@Component({
  selector: 'app-navbar',
  standalone: false,
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  profile?:KeycloakProfile;
  constructor(public keycloak: KeycloakService) {
  }
  ngOnInit() {
    if (this.keycloak.isLoggedIn()){
      this.keycloak.loadUserProfile().then(profile =>{
        this.profile = profile;
        console.log("The user authenticated => ", this.profile);
      })
    }
  }

  logout() {
    this.keycloak.logout();
  }

  async login() {
    await this.keycloak.login({
      redirectUri: window.location.origin
    });
  }
}
