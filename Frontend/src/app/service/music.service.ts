import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class MusicService {
  details: any = { "song_id": null, "imagepath": null, "audiopath": null, "name": null, "artist": null, "isplaying": false }
  heading: string = "";
  private playsong = new Subject<any>();
  private playCheck = new Subject<any>();
  private stopsong = new Subject<any>();
  stopSong$ = this.stopsong.asObservable();
  check$ = this.playCheck.asObservable();
  play$ = this.playsong.asObservable();

  checking() {
    return this.playCheck.next();
  }

  playing() {
    return this.playsong.next();
  }

  stop() {
    return this.stopsong.next();
  }
  
  constructor() { }
}
