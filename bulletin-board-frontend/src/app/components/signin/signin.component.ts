import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {
  signinForm: FormGroup;
  signinErrorMessage: string;
  fieldErrorStyle = '1px #ff0000 solid';
  fieldNotEmptyMessage = 'This field is required';

  constructor(private formBuilder: FormBuilder, private userService: UserService,
              private router: Router) {
    this.signinForm = formBuilder.group({
      email: ['', [Validators.required]],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {
  }

  getFormField(fieldName: string) {
    return this.signinForm.get(fieldName);
  }

  isFormFieldValid(fieldName: string) {
    return this.signinForm.controls[fieldName]?.untouched || this.signinForm.controls[fieldName]?.valid;
  }

  signin() {
    if (this.signinForm.valid) {
      const email = this.signinForm.get('email').value;
      const password = this.signinForm.get('password').value;
      this.userService.signinUser({email, password}).subscribe(resp => {
        const status = resp.status;
        if (status >= 200 && status < 300) {
          localStorage.setItem('authData', JSON.stringify(resp.body));
          this.router.navigate(['/']).then(() => window.location.reload());
        }
      }, error => {
        this.signinErrorMessage = 'Wrong username or password';
      });
    } else {
      this.signinErrorMessage = 'Enter valid values in all fields above';
    }
  }
}
