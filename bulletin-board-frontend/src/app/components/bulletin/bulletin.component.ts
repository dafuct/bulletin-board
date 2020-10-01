import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Bulletin} from '../../models/bulletin';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-bulletin',
  templateUrl: './bulletin.component.html',
  styleUrls: ['./bulletin.component.css']
})
export class BulletinComponent implements OnInit {

  @Input()
  currentUserEmail: string;

  @Input()
  bulletin: Bulletin;

  @Output()
  deleteBulletin: EventEmitter<any> = new EventEmitter<any>();
  imageSource: string = null;

  constructor() {
  }

  ngOnInit(): void {
    if (this.bulletin.image && this.bulletin.image.base64Value) {
      this.imageSource = environment.imageUrl[this.bulletin.image.fileExtension] + this.bulletin.image.base64Value;
    }
  }

  deleteBulletinClick(bulletin: Bulletin) {
    this.deleteBulletin.emit(bulletin);
  }
}
