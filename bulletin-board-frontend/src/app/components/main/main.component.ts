import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {BulletinService} from '../../services/bulletin.service';
import {Bulletin} from '../../models/bulletin';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  shownBulletin: Bulletin[];
  pageSize: number;
  totalResults: number;
  currentPage: number;
  currentUserEmail: string;
  totalPages: number;

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private bulletinService: BulletinService,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.currentUserEmail = this.userService.getAuthenticatedUserEmail(this.userService.getAuthenticationToken());
    this.activatedRoute.queryParams.subscribe(params => {
      this.activatedRoute.data.subscribe(data => {
        data = data.allAds.body;
        if (!data.content.length && params.page && params.page !== 1) {
          this.router.navigate(['/'], {queryParams: {}}).then(() => window.location.reload());
        } else {
          this.shownBulletin = data.content;
          this.currentPage = data.number;
          if (params.page) {
            this.currentPage = params.page;
          }
          this.pageSize = data.size;
          this.totalResults = data.totalElements;
          this.totalPages = data.totalPages;
        }
      }, error => {
        alert('An error occurred');
      });
    });
  }

  onPageChange(page: number) {
    this.currentPage = (page === 1) ? null : page;
    this.router.navigate([], {
      queryParams: {
        ...this.activatedRoute.snapshot.queryParams,
        page: this.currentPage
      },
    }).then();
    window.scroll(0, 0);
  }

  deleteBulletin(bulletin: Bulletin) {
    this.bulletinService.deleteBulletin(bulletin.id).subscribe(response => {
      if (response.status >= 200 && response.status < 300) {
        const index = this.shownBulletin.findIndex(ad => ad.id === bulletin.id);
        this.shownBulletin.splice(index, 1);
        this.totalResults--;
      }
    });
  }
}
