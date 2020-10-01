import {Injectable} from '@angular/core';
import {BulletinService} from './bulletin.service';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BulletinsListResolverService implements Resolve<any> {

  constructor(private bulletinService: BulletinService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> {
    let page = route.queryParams.page;
    if (!page) {
      page = 1;
    }
    return this.bulletinService.getBulletins(page);
  }
}

