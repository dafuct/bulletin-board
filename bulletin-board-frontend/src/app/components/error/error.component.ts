import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {

  note: string;
  message: string;

  constructor(private activatedRoute: ActivatedRoute) {
  }

  private static getMessageByErrorType(type: string): string {
    switch (type) {
      case environment.errorTypes.SERVER:
        return 'An Internal Server Error occurred';
      case environment.errorTypes.FORBIDDEN:
        return 'FORBIDDEN';
      case environment.errorTypes.OTHER:
      default:
        return 'An error occurred';
    }
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      this.message = ErrorComponent.getMessageByErrorType(params.type);
      if (params.type === environment.errorTypes.OTHER) {
        this.note = 'Note: an AdBlock disable';
      }
    });
  }
}
