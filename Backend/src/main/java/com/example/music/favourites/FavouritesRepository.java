package com.example.music.favourites;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface FavouritesRepository extends JpaRepository<Favourites,Long> {

    @Query(value = "select id,audio_name,audio_image,artist,movie_name from songs "
            + "where id in(select song from favourites where user=?1)", nativeQuery = true)
    List<Object[]> findFavouriteSongs(Long id);

    @Query(value="select song from favourites where user=?1",nativeQuery = true)
    List<Long> findFavouriteSongsId(Long key2);

    @Transactional
    @Modifying
    @Query(value="delete from favourites where user=?1 and song=?2",nativeQuery = true)
    void deleteFavouriteSong(Long user_id,Long song_id);

}

