import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { SongModel } from '../model/song-model';
import { HttpService } from '../service/http.service';
import { HttpClient } from '@angular/common/http';

declare var $: any;

@Component({
  selector: 'app-manage-files',
  templateUrl: './manage-files.component.html',
  styleUrls: ['./manage-files.component.css']
})
export class ManageFilesComponent implements OnInit {
  songModel = new SongModel();
  selectedFile: File;
  selectedImage: File;
  user: string = localStorage.getItem("username");

  constructor(private http: HttpService, private httpClient: HttpClient) { }

  ngOnInit(): void {
    $("#alert").hide();
  }

  public onFileChanged(event) {
    this.selectedFile = event.target.files[0];
  }

  alertclose() {
    $("#alert").hide();
  }

  public OnImageChanged(event) {
    this.selectedImage = event.target.files[0];
  }

  SongForm(form: NgForm) {
    const uploadData = new FormData();
    uploadData.append('audiofile', this.selectedFile);
    uploadData.append('audioimage', this.selectedImage);
    this.songModel.audio_name = form.controls["songname"].value
    this.songModel.artist = form.controls["singerinfo"].value;
    this.songModel.movie_name = form.controls["movie"].value;
    uploadData.append('songmodel', JSON.stringify(this.songModel));
    this.http.uploadSong(uploadData).subscribe((data) => {
      if (data == 1) {
        $("#alert").show();
        form.controls["songname"].setValue("");
        form.controls["singerinfo"].setValue("");
        form.controls["movie"].setValue("");
        this.songModel.category = "";
        this.songModel.language = "";
        (<HTMLInputElement>document.getElementById("audioFile")).value = "";
        (<HTMLInputElement>document.getElementById("audioImage")).value = "";
      }
    })
  }
}





