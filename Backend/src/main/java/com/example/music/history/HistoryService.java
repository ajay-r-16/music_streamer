package com.example.music.history;

import com.example.music.song.Song;
import com.example.music.song.SongService;
import com.example.music.user.Users;
import com.example.music.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final SongService songService;
    private final UserService userService;

    public HistoryService(HistoryRepository historyRepository, SongService songService, UserService userService) {
        this.historyRepository = historyRepository;
        this.songService = songService;
        this.userService = userService;
    }

    public List<Object[]> getHistory(String email){
        Users user = userService.getUserByEmail(email).get();
        List<Object[]> result=historyRepository.findHistory(user.getId());
        for(Object[] res:result) {
            res[2]=songService.decompressBytes((byte[]) res[2]);
        }
        return result;
    }

    public List<Object[]> getAllHistory(String email){
        Users user = userService.getUserByEmail(email).get();
        List<Object[]> result=historyRepository.findAllHistory(user.getId());
        for(Object[] res:result) {
            res[2]=songService.decompressBytes((byte[]) res[2]);
        }
        return result;
    }

    public int addHistory(String email,Long songId) {
        Users user = userService.getUserByEmail(email).get();
        Long userId = user.getId();
        Song song = songService.getSongById(songId);
        checkWhetherSongIsPresent(userId, songId);
        int count= historyRepository.findCountOfSongsInHistory(userId);
        History history = new History();
        history.setAccount(user);
        history.setSongs(song);
        if(count<10) {
            historyRepository.save(history);
        }
        else {
            Long s=historyRepository.findFirstSongFromHistory(userId);
            historyRepository.removeSongFromHistory(s);
            historyRepository.save(history);
        }

        return 1;
    }

    public void checkWhetherSongIsPresent(Long id1,Long id2) {
        try {
            Long id=historyRepository.getHistorySongById(id1, id2);
            historyRepository.removeSongFromHistory(id);
        }
        catch (Exception NoSuchElementException) {
        }

    }
}
