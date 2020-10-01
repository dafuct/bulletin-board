import {Component, OnInit} from '@angular/core';
import {User} from '../../models/user';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../../services/user.service';
import {BulletinService} from '../../services/bulletin.service';
import {BulletinCreateForm} from '../../models/bulletin-create-form';
import {UserEditForm} from '../../models/user-edit-form';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  user: User;
  isEmail = false;

  constructor(private activatedRoute: ActivatedRoute, private userService: UserService,
              private bulletinService: BulletinService) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.user = data.userInfo.body;
    }, error => {
      alert('An error occurred');
    });
  }

  createBulletin(form: BulletinCreateForm) {
    if (form.error) {
      alert(form.error);
    } else {
      this.bulletinService.createBulletin(form).subscribe(response => {
        const status = response.status;
        if (status >= 200 && status < 300) {
          alert('Bulletin created!');
        }
      });
    }
  }

  updateUser(userEditForm: UserEditForm) {
    if (userEditForm.error) {
      alert(userEditForm.error);
    } else {
      if (userEditForm.email && userEditForm.email === this.user.email) {
        this.isEmail = false;
      } else if (userEditForm.email && this.checkIfEmailIsTaken(userEditForm.email)) {
        this.isEmail = true;
      } else {
        this.userService.editUser(userEditForm).subscribe(response => {
          const status = response.status;
          if (status >= 200 && status < 300) {
            alert('User updated!');
            if (userEditForm.password) {
              this.userService.logoutUser();
            }
            window.location.reload();
          }
        });
      }
    }
  }

  checkIfEmailIsTaken(email: string): boolean {
    this.userService.checkIfEmailExists(email).subscribe(response => {
      const status = response.status;
      if (status >= 200 && status < 300) {
        return response.body.taken;
      }
    }, error => {
      alert('An error occurred');
    });
    return false;
  }
}
