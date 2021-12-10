import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MusicService } from '../service/music.service';
import { HttpService } from '../service/http.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  user: string = localStorage.getItem('username');
  key: string = null;

  constructor(private router: Router, private http: HttpService, private music: MusicService) { }

  ngOnInit(): void {}

  logout() {
    sessionStorage.removeItem('username')
    sessionStorage.removeItem('token')
    localStorage.removeItem('username')
    localStorage.removeItem('token')
    this.music.details['isplaying'] = false;
    this.music.stop();
    this.router.navigate(["/"]);
  }
}
