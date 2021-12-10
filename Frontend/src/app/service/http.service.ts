import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { UserModel } from '../model/user-model';
import { OtpModel } from '../model/otp-model';

@Injectable({
  providedIn: 'root'
})

export class HttpService {
  private baseurl = 'http://localhost:8081/'
  Account_id: number = null;
  user: string = sessionStorage.getItem('uername');
  constructor(private http: HttpClient) {}

  register(userModel: UserModel) {
    var url = this.baseurl + "sign-up"
    return this.http.post(url, userModel)
  }

  verify(otpmodel: OtpModel) {
    var url = this.baseurl + "verify"
    return this.http.post(url, otpmodel)
  }

  login(userModel: UserModel) {
    var url = this.baseurl + "login"
    return this.http.post(url, userModel)
  }

  getuser(email: String) {
    var url = this.baseurl + "get_user"
    return this.http.post(url, email);
  }

  verifyPassCode(otpmodel: OtpModel) {
    var url = this.baseurl + "verify"
    return this.http.post(url, otpmodel);
  }

  changePassword(usermodel: UserModel) {
    var url = this.baseurl + "change_password"
    return this.http.post(url, usermodel);
  }

  resendOtp(usermodel: UserModel) {
    var url = this.baseurl + "resend_otp"
    return this.http.post(url, usermodel);
  }

  uploadSong(imagefile: FormData) {
    var url = this.baseurl + "upload_song"
    return this.http.post(url, imagefile);
  }

  getLatestSongs() {
    var url = this.baseurl + "get_latest"
    var header = {
      headers: new HttpHeaders().set('Authorization', localStorage.getItem('token'))
    }
    return this.http.get(url, header);
  }

  getRecent() {
    var url = this.baseurl + "get_recent"
    var header = {
      headers: new HttpHeaders().set('Authorization', localStorage.getItem('token'))
    }
    return this.http.get(url, header);
  }

  getAllLatestSongs() {
    var url = this.baseurl + "get_all_latest"
    var header = {
      headers: new HttpHeaders().set('Authorization', sessionStorage.getItem('token'))
    }
    return this.http.get(url, header);
  }

  playMusic(id: number) {
    var url = this.baseurl + "get_song"
    var header = {
      headers: new HttpHeaders().set('Authorization', localStorage.getItem('token'))
    }
    return this.http.post(url, id, header);
  }

  addFavourites(song_id: number) {
    var url = this.baseurl + "add_fav/";
    const headers = new HttpHeaders().set('Authorization', localStorage.getItem('token'));
    const httpParams = new HttpParams().set('email', localStorage.getItem('username')).set('song', String(song_id));
    const options = { params: httpParams, headers: headers };
    return this.http.get(url, options);
  }
  
  removeFav(song_id: number) {
    var url = this.baseurl + "remove_fav/";
    const headers = new HttpHeaders().set('Authorization', localStorage.getItem('token'));
    const httpParams = new HttpParams().set('email', localStorage.getItem('username')).set('song', String(song_id));
    const options = { params: httpParams, headers: headers };
    return this.http.get(url, options);
  }

  addRecent(song_id: number) {
    var url = this.baseurl + "add_recent";
    const headers = new HttpHeaders().set('Authorization', localStorage.getItem('token'));
    const httpParams = new HttpParams().set('email', localStorage.getItem('username')).set('song', String(song_id));
    const options = { params: httpParams, headers: headers };
    return this.http.get(url, options);
  }

  playnext(id: number) {
    var url = this.baseurl + "play_next";
    var header = {
      headers: new HttpHeaders().set('Authorization', localStorage.getItem('token'))
    }
    return this.http.post(url, id, header);
  }

  playprev(id: number) {
    var url = this.baseurl + "play_prev";
    var header = {
      headers: new HttpHeaders().set('Authorization', localStorage.getItem('token'))
    }
    return this.http.post(url, id, header);
  }

  getFav() {
    var url = this.baseurl + "favourites"
    var header = {
      headers: new HttpHeaders().set('Authorization', localStorage.getItem('token'))
    }
    return this.http.get(url, header)
  }

  common(term: string, subterm: string) {
    if (subterm) {
      var url = this.baseurl + "get_" + term
      const headers = new HttpHeaders().set('Authorization', localStorage.getItem('token'));
      const httpParams = new HttpParams().set('search', subterm.toLowerCase())
      const options = { params: httpParams, headers: headers }
      return this.http.get(url, options);
    }
    else {
      var url = this.baseurl + "get_all_" + term
      var header = {
        headers: new HttpHeaders().set('Authorization', localStorage.getItem('token'))
      }
      return this.http.get(url, header);
    }
  }
}
