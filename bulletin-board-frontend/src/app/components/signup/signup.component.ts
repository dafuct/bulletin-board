import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signupForm: FormGroup;
  signupErrorMessage: string;
  fieldNotEmptyMessage = 'This field is required';
  fieldErrorStyle = '1px #ff0000 solid';

  constructor(private formBuilder: FormBuilder, private userService: UserService,
              private router: Router) {
    this.signupForm = formBuilder.group({
      firstName: ['', [Validators.required, Validators.maxLength(50), Validators.pattern('^[A-Za-z\-]+$')]],
      lastName: ['', [Validators.required, Validators.maxLength(100), Validators.pattern('^[A-Za-z\-]+$')]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(100)]],
      confirmPassword: ['', Validators.required]
    }, {
      validators: this.mustMatch('password', 'confirmPassword')
    });
  }

  private mustMatch(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];
      if (!matchingControl.errors.mustMatch && matchingControl.errors) {
        return;
      }
      if (control.value !== matchingControl.value) {
        matchingControl.setErrors({mustMatch: true});
      } else {
        matchingControl.setErrors(null);
      }
    };
  }

  ngOnInit(): void {
  }

  getFormField(fieldName: string) {
    return this.signupForm.get(fieldName);
  }

  isFormFieldValid(fieldName: string) {
    return this.signupForm.controls[fieldName]?.untouched || this.signupForm.controls[fieldName]?.valid;
  }

  signup() {
    if (this.signupForm.valid) {
      const user = this.signupForm.value;
      this.userService.signupUser(user).subscribe(response => {
        const status = response.status;
        if (status >= 200 && status < 300) {
          alert('Successful registration!');
          this.router.navigate(['/signin']).then(() => window.location.reload());
        } else if (status >= 400 && status < 500) {
          alert(response.error.message);
        }
      });
    } else {
      this.signupErrorMessage = 'Enter valid values in all fields above';
    }
  }

  checkIfEmailExists(input) {
    const email = input.target.value;
    if (email) {
      this.userService.checkIfEmailExists(email).subscribe(resp => {
        if (resp.status >= 200 && resp.status < 300) {
          if (resp.body.taken) {
            this.signupForm.get('email').setErrors({taken: true});
          }
        }
      });
    }
  }
}
