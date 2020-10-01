import {Component, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isUserAuthenticated: boolean;

  constructor(private router: Router, private userService: UserService) {
    this.isUserAuthenticated = userService.isUserAuthenticated(userService.getAuthenticationToken());
  }

  ngOnInit(): void {
  }

  logout(event: Event): void {
    event.preventDefault();
    this.userService.logoutUser();
    this.router.navigate(['/']).then(() => window.location.reload());
  }
}
