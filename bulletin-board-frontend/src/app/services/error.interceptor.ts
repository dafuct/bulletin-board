import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {UserService} from './user.service';
import {catchError} from 'rxjs/operators';
import {Router} from '@angular/router';
import {environment} from '../../environments/environment';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private userService: UserService, private router: Router) {
  }

  private static getErrorTypeByCode(code: number): string {
    if (code === 403) {
      return environment.errorTypes.FORBIDDEN;
    } else if (code >= 500) {
      return environment.errorTypes.SERVER;
    } else {
      return environment.errorTypes.OTHER;
    }
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(catchError(err => {
      if (err.status === 400 || err.status === 404) {
        this.router.navigateByUrl('/not-found').then();
      } else if (err.status === 401) {
        this.userService.logoutUser();
        window.location.reload();
      } else {
        const type = ErrorInterceptor.getErrorTypeByCode(err.status);
        this.router.navigate(['/error'], {
          queryParams: {type}
        }).then();
      }
      if (err.error && err.error.message) {
        return throwError(err.error.message);
      }
      return throwError(err);
    }));
  }
}
