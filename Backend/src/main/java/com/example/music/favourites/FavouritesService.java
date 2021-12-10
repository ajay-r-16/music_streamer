package com.example.music.favourites;

import com.example.music.song.SongService;
import com.example.music.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouritesService {

    private final FavouritesRepository favouritesRepository;
    private final SongService songService;
    private final UserService userService;

    public FavouritesService(FavouritesRepository favouritesRepository, SongService songService, UserService userService) {
        this.favouritesRepository = favouritesRepository;
        this.songService = songService;
        this.userService = userService;
    }

    public List<Object[]> getFavouriteSongs(Long id){
        List<Object[]> result=favouritesRepository.findFavouriteSongs(id);
        for(Object[] res:result) {
            res[2]=songService.decompressBytes((byte[]) res[2]);
        }
        return result;
    }

    public List<Long> getFavouriteSongsId(Long key){
        return favouritesRepository.findFavouriteSongsId(key);
    }

    public void addFavourites(String email, Long songId){
        Favourites favourites = new Favourites();
        favourites.setAccount(userService.getUserByEmail(email).get());
        favourites.setSongs(songService.getSongById(songId));
        favouritesRepository.save(favourites);
    }

    public void removeFavourite(String email,Long songId) {
        favouritesRepository.deleteFavouriteSong(userService.getUserByEmail(email).get().getId(), songId);
    }
}
