package com.example.music.song;

import com.example.music.dto.SongsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping("/upload_song")
    public int uploadImage(@RequestParam("audiofile") MultipartFile file, @RequestParam("audioimage") MultipartFile image, @RequestParam("songmodel") String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SongsDto songsDto = mapper.readValue(json, SongsDto.class);
        songService.saveSong(file, image, songsDto);
        return 1;
    }

    @GetMapping("/get_latest")
    public List<Object[]> getLatest() {
        return songService.getLatestSongs();
    }

    @PostMapping("/get_song")
    public List<Object[]> getSong(@RequestBody Long id) {
        return songService.getSong(id);
    }

    @GetMapping("/get_all_songs")
    public List<Object[]> getAllLatest() {
        return songService.getAllLatestSongs();
    }

    @PostMapping("/play_next")
    public List<Object[]> playNext(@RequestBody Long id) {

        return songService.getNextSong(id);

    }

    @PostMapping("/play_prev")
    public List<Object[]> playPrev(@RequestBody Long id) {

        return songService.getPrevSong(id);

    }

    @GetMapping("/get_category")
    public List<Object[]> getCategory(@RequestParam("search") String key) {
        return songService.getCategory(key);

    }

    @GetMapping("/get_language")
    public List<Object[]> getLanguage(@RequestParam("search") String key) {
        return songService.getLanguage(key);

    }

    @GetMapping("/get_search")
    public List<Object[]> Search(@RequestParam("search") String key) {
        return songService.Search(key);
    }
}
