import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SigninForm} from '../models/signin-form';
import {JwtHelperService} from '@auth0/angular-jwt';
import {SignupForm} from '../models/signup-form';
import {UserEditForm} from '../models/user-edit-form';
import {environment} from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private urlBase = environment.urlBase;

  constructor(private http: HttpClient) {
  }

  signupUser(registerForm: SignupForm): Observable<any> {
    return this.http.post(this.urlBase + '/signup', registerForm, {observe: 'response'});
  }

  isUserAuthenticated(token: string): boolean {
    const jwtHelperService = new JwtHelperService();
    try {
      return !jwtHelperService.isTokenExpired(token);
    } catch (e) {
      return false;
    }
  }

  getAuthenticatedUserEmail(token: string): string {
    const jwtHelperService = new JwtHelperService();
    try {
      return jwtHelperService.decodeToken(token).sub;
    } catch (e) {
      return null;
    }
  }

  signinUser(signinForm: SigninForm): Observable<any> {
    return this.http.post(this.urlBase + '/signin', signinForm, {observe: 'response'});
  }

  logoutUser(): void {
    localStorage.removeItem('authData');
  }

  getAuthenticationToken(): string {
    if (localStorage.getItem('authData') === null) {
      return null;
    }
    return JSON.parse(localStorage.getItem('authData')).token;
  }

  getAuthenticationTokenType(): string {
    if (localStorage.getItem('authData') === null) {
      return null;
    }
    return JSON.parse(localStorage.getItem('authData')).type;
  }

  getUserInfo(): Observable<any> {
    return this.http.get(this.urlBase + '/user', {observe: 'response'});
  }

  editUser(userEditForm: UserEditForm): Observable<any> {
    return this.http.put(this.urlBase + '/user', userEditForm, {observe: 'response'});
  }

  checkIfEmailExists(email: string): Observable<any> {
    return this.http.get(this.urlBase + `/user/status?email=${email}`, {observe: 'response'});
  }
}
