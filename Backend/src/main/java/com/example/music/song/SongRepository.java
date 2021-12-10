package com.example.music.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song,Long> {

    @Query(value = "select id,audio_name,audio_image,artist from songs order by id desc limit 10", nativeQuery = true)
    List<Object[]> findLatestSongs();

    @Query(value = "select id,audio_file from songs where id=?1",nativeQuery = true)
    List<Object[]> playSong(Long id);

    @Query(value = "select id,audio_name,audio_image,artist,movie_name from songs order by id desc limit 20", nativeQuery = true)
    List<Object[]> findAllLatestSongs();

    @Query(value = "select id,audio_name,audio_image,artist,movie_name from songs where category=?1",nativeQuery = true)
    List<Object[]> findCategory(String key);

    @Query(value = "select id,audio_name,audio_image,artist,movie_name from songs where language=?1",nativeQuery = true)
    List<Object[]> findLanguage(String key);

    @Query(value="select id from songs limit 1",nativeQuery = true)
    Long findFirstSong();

    @Query(value = "select id,audio_name,audio_image,audio_file,artist from songs "
            + "where id=?1",nativeQuery = true)
    List<Object[]> playNextSong(Long id);

    @Query(value = "select id,audio_name,audio_image,artist,movie_name from songs where lower(audio_name) like ?1%"
            + " or lower(movie_name) like ?1% or lower(artist) like ?1%",nativeQuery = true)
    List<Object[]> searchSong(String key);

    @Query(value="select id from songs order by id desc limit 1",nativeQuery = true)
    Long findLastSong();
}