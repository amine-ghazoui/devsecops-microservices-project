import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './components/home/home.component';
import { ProduitListComponent } from './components/produits/produit-list/produit-list.component';
import { ProduitFormComponent } from './components/produits/produit-form/produit-form.component';
import { CommandeListComponent } from './components/commandes/commande-list/commande-list.component';
import { CommandeDetailsComponent } from './components/commandes/commande-details/commande-details.component';
import { CommandeFormComponent } from './components/commandes/commande-form/commande-form.component';
import {KeycloakAngularModule, KeycloakService} from 'keycloak-angular';
function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8080',
        realm: 'ecommerce-realm',
        clientId: 'frontend-client'
      },
      initOptions: {
        onLoad: 'login-required',
        silentCheckSsoRedirectUri:
          window.location.origin + '/silent-check-sso.html'
      },
      enableBearerInterceptor: true,
      bearerPrefixes: ['/api', 'http://localhost:8888/api'],
    });
}
@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    ProduitListComponent,
    ProduitFormComponent,
    CommandeListComponent,
    CommandeDetailsComponent,
    CommandeFormComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    AppRoutingModule,
    HttpClientModule,
    KeycloakAngularModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
