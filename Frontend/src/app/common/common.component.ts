import { Component, OnInit, ViewChild } from '@angular/core';
import { MusicService } from '../service/music.service';
import { HttpService } from '../service/http.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-common',
  templateUrl: './common.component.html',
  styleUrls: ['./common.component.css']
})

export class CommonComponent implements OnInit {
  title: string;
  details: any;
  fav: any;
  checking: any = [1, 2, 3];
  isloaded: boolean = false;
  @ViewChild(FooterComponent) footer: FooterComponent;

  constructor(private music: MusicService, private http: HttpService, private router: ActivatedRoute,private route: Router){}

  ngOnInit(): void {
    if (localStorage.getItem('username') == null) {
      this.route.navigate(['']);
    }
    this.router.params.subscribe(params => {
      if (params['term'] == "category" || params['term'] == "language") {
        this.title = params['term2'][0].toUpperCase() + params['term2'].slice(1) + " Songs";
      }
      else if (params['term'] == "songs") {
        this.title = "Latest Songs";
      }
      else if (params['term'] == "history") {
        this.title = "Recently Played";
      }
      else {
        this.title = params['term'][0].toUpperCase() + params['term'].slice(1)
      }
      this.http.getFav().subscribe((data) => {
        this.fav = data;
      })
      this.http.common(params['term'], params['term2']).subscribe((data) => {
        this.details = data;
        this.isloaded = true;
      })
    });
  }

  addFav(song_id: number) {
    if (this.fav.includes(song_id)) {
      let index = this.fav.indexOf(song_id);
      if (index > -1) {
        this.fav.splice(index, 1);
      }
      this.http.removeFav(song_id).subscribe((data) => { })
    }
    else {
      this.fav.push(song_id);
      this.http.addFavourites(song_id).subscribe((data) => { })
    }
  }

  playMusic(song: any) {
    this.music.stop();
    this.music.details["song_id"] = song[0];
    this.music.details["name"] = song[1];
    this.music.details["imagepath"] = "data:image/jpeg;base64," + song[2];
    this.music.details["artist"] = song[3];
    this.http.playMusic(song[0]).subscribe((data) => {
      this.music.details["audiopath"] = data[0][1];
      this.music.playing();
    })
  }
}
