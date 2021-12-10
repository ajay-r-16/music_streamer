package com.example.music.history;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("/get_recent")
    public List<Object[]> getRecent() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String email = userDetails.getUsername();
        return historyService.getHistory(email);

    }

    @GetMapping("/get_all_history")
    public List<Object[]> getAllRecent() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String email = userDetails.getUsername();
        return historyService.getAllHistory(email);

    }

    @GetMapping(value = "/add_recent")
    public int addRecent(@RequestParam("email") String email, @RequestParam("song") String song) {
        Long songId = Long.parseLong(song);

        historyService.addHistory(email, songId);
        return 1;

    }
}
