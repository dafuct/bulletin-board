import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NgModel} from '@angular/forms';

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.css']
})
export class UserSettingsComponent implements OnInit {

  firstNameValue: string;
  lastNameValue: string;
  emailValue: string;
  passwordValue: string;
  confirmPasswordValue: string;

  @Input()
  currentFirstName: string;
  @Input()
  currentLastName: string;
  @Input()
  currentEmail: string;
  @Input()
  isEmail: boolean;

  @Output()
  saveField: EventEmitter<any> = new EventEmitter<any>();

  constructor() {
  }

  ngOnInit(): void {
    this.firstNameValue = this.currentFirstName;
    this.lastNameValue = this.currentLastName;
    this.emailValue = this.currentEmail;
  }

  checkIfPasswordsMatch() {
    return this.passwordValue === this.confirmPasswordValue;
  }

  saveFieldClick(ngModel: NgModel) {
    if (ngModel.control.valid) {
      this.saveField.emit({[ngModel.name]: ngModel.control.value});
    } else {
      this.saveField.emit({error: 'Entered data is invalid'});
    }
  }
}
