package com.example.music.history;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface HistoryRepository extends JpaRepository<History,Long> {

    @Query(value = "select count(*) from history where user= ?1",nativeQuery = true)
    int findCountOfSongsInHistory(Long id);

    @Query(value = "select id from history where user= ?1 limit 1",nativeQuery = true)
    Long findFirstSongFromHistory(Long id);

    @Transactional
    @Modifying
    @Query(value="delete from history where id=?1",nativeQuery = true)
    void removeSongFromHistory(Long id);

    @Query(value="select id,audio_name,audio_image,artist from songs "
            + "where id in(select song from history where user=?1 order by id) limit 8",nativeQuery = true)
    List<Object[]> findHistory(Long id);

    @Query(value="select id,audio_name,audio_image,artist,movie_name from songs "
            + "where id in(select song from history where user=?1) limit 13",nativeQuery = true)
    List<Object[]> findAllHistory(Long id);

    @Query(value="select id from history where user=?1 and song=?2",nativeQuery = true)
    Long getHistorySongById(Long userId,Long songId);


}

