package com.example.music.favourites;

import com.example.music.song.SongService;
import com.example.music.user.Users;
import com.example.music.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class FavouritesController {

    @Autowired
    private UserService userService;

    @Autowired
    private FavouritesService favouritesService;

    @Autowired
    private SongService songService;

    @GetMapping("/get_all_favourites")
    public List<Object[]> favourites() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String email = userDetails.getUsername();
        Users user = userService.getUserByEmail(email).get();
        return favouritesService.getFavouriteSongs(user.getId());
    }

    @GetMapping("/favourites")
    public List<Long> favouritesId() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String email = userDetails.getUsername();
        Users user = userService.getUserByEmail(email).get();
        return favouritesService.getFavouriteSongsId(user.getId());
    }

    @GetMapping(value = "/add_fav")
    public int addFav(@RequestParam("email") String email,
                      @RequestParam("song") String song) {
        Long songId = Long.parseLong(song);
        favouritesService.addFavourites(email, songId);
        return 1;
    }

    @GetMapping(value = "/remove_fav")
    public int removeFav(@RequestParam("email") String email,
                         @RequestParam("song") String song) {
        Long songId = Long.parseLong(song);

        favouritesService.removeFavourite(email, songId);
        return 1;
    }
}
