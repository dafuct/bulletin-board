import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {UserService} from './user.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(private userService: UserService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.userService.getAuthenticationToken();
    const tokenType = this.userService.getAuthenticationTokenType();
    if (token && tokenType) {
      req = req.clone({
        setHeaders: {
          Authorization: `${tokenType} ${token}`
        }
      });
    }
    return next.handle(req);
  }

}
