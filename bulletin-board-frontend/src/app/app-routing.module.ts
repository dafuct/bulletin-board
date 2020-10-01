import {NgModule} from '@angular/core';
import {Routes, RouterModule, ActivatedRouteSnapshot} from '@angular/router';
import {SigninComponent} from './components/signin/signin.component';
import {SignupComponent} from './components/signup/signup.component';
import {MainComponent} from './components/main/main.component';
import {UserProfileComponent} from './components/user-profile/user-profile.component';
import {BulletinsListResolverService} from './services/bulletins-list-resolver.service';
import {AuthGuard} from './services/auth.guard';
import {UserNotFoundComponent} from './components/user-not-found/user-not-found.component';
import {UserInfoResolverService} from './services/user-info-resolver.service';
import {ErrorComponent} from './components/error/error.component';

const routes: Routes = [
  {
    path: '', component: MainComponent, resolve: {allAds: BulletinsListResolverService},
    runGuardsAndResolvers: (current: ActivatedRouteSnapshot, future: ActivatedRouteSnapshot) => {
      return current.queryParams.page !== future.queryParams.page;
    }
  },
  {path: 'signin', component: SigninComponent},
  {path: 'signup', component: SignupComponent},
  {path: 'account', component: UserProfileComponent, canActivate: [AuthGuard], resolve: {userInfo: UserInfoResolverService}},
  {path: 'error', component: ErrorComponent},
  {path: '**', component: UserNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
