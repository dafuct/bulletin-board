import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HeaderComponent} from './components/header/header.component';
import {SigninComponent} from './components/signin/signin.component';
import {MainComponent} from './components/main/main.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CreateBulletinComponent} from './components/create-bulletin/create-bulletin.component';
import {UserSettingsComponent} from './components/user-settings/user-settings.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {JwtInterceptor} from './services/jwt.interceptor';
import {ErrorInterceptor} from './services/error.interceptor';
import {UserNotFoundComponent} from './components/user-not-found/user-not-found.component';
import {NgbPaginationModule} from '@ng-bootstrap/ng-bootstrap';
import {AppRoutingModule} from './app-routing.module';
import {SignupComponent} from './components/signup/signup.component';
import {BulletinComponent} from './components/bulletin/bulletin.component';
import {ErrorComponent} from './components/error/error.component';
import {UserProfileComponent} from './components/user-profile/user-profile.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SigninComponent,
    SignupComponent,
    MainComponent,
    UserProfileComponent,
    BulletinComponent,
    CreateBulletinComponent,
    UserSettingsComponent,
    UserNotFoundComponent,
    ErrorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgbPaginationModule,
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
