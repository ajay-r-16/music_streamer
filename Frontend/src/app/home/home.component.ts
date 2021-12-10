import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { HttpService } from '../service/http.service';
import { Router } from '@angular/router';

import { MusicService } from '../service/music.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  user: string;
  checking: any = [1, 2, 3, 4, 5];
  recently_added: any;
  recently_played: any;
  isloaded: boolean = false;

  Categories: any = [["Melody", "assets/melody.png"], ['Pop', "assets/pop.png"],
  ['Classical', "assets/classical.jpg"], ['Rock', "assets/rock.jpg"]];

  Languages: any = [["English", "assets/english1.png"], ["Tamil", "assets/tamil1.png"],
  ["Hindi", "assets/hindi1.png"], ["Telugu", "assets/telugu1.png"]];

  constructor(private http: HttpService, private router: Router, private music: MusicService) { }

  ngOnInit(): void {
    if (localStorage.getItem('username') == null) {
      this.router.navigate(['']);
    }
    this.http.getLatestSongs().subscribe((data) => {
      this.isloaded = true;
      this.recently_added = data;
    })
    this.http.getRecent().subscribe((data) => {
      this.recently_played = data;
      this.recently_played = this.recently_played.reverse();

    })
  }

  playMusic(song: any) {
    this.music.checking();
    this.music.details["song_id"] = song[0];
    this.music.details["name"] = song[1];
    this.music.details["imagepath"] = "data:image/jpeg;base64," + song[2];
    this.music.details["artist"] = song[3];
    this.http.playMusic(song[0]).subscribe((data) => {
      this.music.details["audiopath"] = data[0][1];
      this.music.details["isplaying"] = true;
      this.music.playing();
    })
  }
}
