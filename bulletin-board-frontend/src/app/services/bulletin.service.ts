import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {BulletinCreateForm} from '../models/bulletin-create-form';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BulletinService {

  private urlBase = environment.urlBase;
  private pageSize = environment.bulletinsPageSize;

  constructor(private http: HttpClient) {
  }

  getBulletins(page: number): Observable<any> {
    if (page < 1 || !page) {
      page = 1;
    }
    return this.http.get(
      this.urlBase + `/bulletins?page=${page}&size=${this.pageSize}`, {observe: 'response'});
  }

  createBulletin(bulletins: BulletinCreateForm) {
    return this.http.post(this.urlBase + '/bulletins', bulletins, {observe: 'response'});
  }

  deleteBulletin(id: number) {
    return this.http.delete(this.urlBase + `/bulletins/${id}`, {observe: 'response'});
  }
}
