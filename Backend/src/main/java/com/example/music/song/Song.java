package com.example.music.song;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "SONGS")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "AUDIO_NAME", length = 50)
    private String audio_name;

    @Lob
    @Column(name = "AUDIO_IMAGE",length = 1000)
    private byte[] audio_image;

    @Lob
    @Column(name = "AUDIO_FILE",length = 1000)
    private byte[] audio_file;

    @Column(name = "CATEGORY", length=100)
    private String category;

    @Column(name = "LANGUAGE", length=100)
    private String language;

    @Column(name = "MOVIE_NAME", length=100)
    private String movie_name;

    @Column(name = "ARTIST", length=100)
    private String artist;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getAudioImage() {
        return audio_image;
    }

    public void setAudioImage(byte[] data) {
        this.audio_image = data;
    }

    public byte[] getAudioFile() {
        return audio_file;
    }

    public void setAudioFile(byte[] data) {
        this.audio_file = data;
    }

    public String getAudioName() {
        return audio_name;
    }

    public void setAudioName(String audio_name) {
        this.audio_name = audio_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMovieName() {
        return movie_name;
    }

    public void setMovieName(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

}
