import { Component } from '@angular/core';

import { MusicService } from '../service/music.service';
import { Howl } from 'howler';
import { HttpService } from '../service/http.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent {

  musicDetails: any = this.music.details;
  player: Howl = null;
  ispause: boolean = false;
  progress: number = 0;
  path: string;

  constructor(private music: MusicService, private http: HttpService) {

    this.music.check$.subscribe(() => {
      if (this.player) {
        this.player.stop();
      }
    })
    this.music.play$.subscribe(() => {
      this.playmusic();
    })
    this.music.stopSong$.subscribe(() => {
      if (this.player) {
        this.player.stop();
      }
    })
  }
  
  playmusic() {
    this.progress = 0;
    if (this.player) {
      this.player.stop();
    }
    this.http.addRecent(this.music.details["song_id"]).subscribe((data) => {})
    this.ispause = false;
    this.player = new Howl({
      src: ["data:audio/mp3;base64," + this.music.details["audiopath"]],
      onend: function () {
        this.playNext();
      }
    });
    this.player.play();
    this.updateProgress();
    this.music.details["isplaying"] = true;
  }

  seek() {
    let newvalue = +this.progress;
    let duration = this.player.duration();
    this.player.seek(duration * (newvalue / 3000));
  }

  updateProgress() {
    let seek = this.player.seek();
    this.progress = (seek / this.player.duration()) * 3000 || 0;
    setTimeout(() => {
      this.updateProgress();
    }, 1000)
  }

  toggle(pause: boolean) {
    this.ispause = pause;
    if (pause) {
      this.player.pause();
    }
    else {
      this.player.play();
    }
  }

  playNext() {
    if (this.player) {
      this.player.stop();
    }
    this.http.playnext(this.musicDetails["song_id"] + 1).subscribe((data) => {
      this.musicDetails["song_id"] = data[0][0];
      this.musicDetails["name"] = data[0][1];
      this.musicDetails["imagepath"] = "data:image/jpeg;base64," + data[0][2];
      this.musicDetails["audiopath"] = data[0][3];
      this.musicDetails["artist"] = data[0][4];
      this.playmusic();
    })
  }

  playprev() {
    if (this.player) {
      this.player.stop();
    }
    this.http.playprev(this.musicDetails["song_id"] - 1).subscribe((data) => {
      this.musicDetails["song_id"] = data[0][0];
      this.musicDetails["name"] = data[0][1];
      this.musicDetails["imagepath"] = "data:image/jpeg;base64," + data[0][2];
      this.musicDetails["audiopath"] = data[0][3];
      this.musicDetails["artist"] = data[0][4];
      this.playmusic();
    })
  }
}
