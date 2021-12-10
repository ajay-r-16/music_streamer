package com.example.music.history;


import com.example.music.song.Song;
import com.example.music.user.Users;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "HISTORY")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "song")
    private Song song ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public Users getAccount() {
        return user;
    }

    public void setAccount(Users user) {
        this.user = user;
    }

    public Song getSongs() {
        return song;
    }

    public void setSongs(Song song) {
        this.song = song;
    }


}
