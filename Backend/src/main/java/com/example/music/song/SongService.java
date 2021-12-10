package com.example.music.song;

import com.example.music.dto.SongsDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class SongService {

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public void saveSong(MultipartFile file, MultipartFile image, SongsDto songsDto) throws IOException {
        Song song = new Song();
        song.setAudioFile(compressBytes(file.getBytes()));
        song.setAudioImage(compressBytes(image.getBytes()));
        song.setArtist(songsDto.getartist());
        song.setAudioName(songsDto.getaudio_name());
        song.setMovieName(songsDto.getmovie_name());
        song.setCategory(songsDto.getcategory());
        song.setLanguage(songsDto.getlanguage());
        songRepository.save(song);
    }

    public List<Object[]> getSong(Long id) {
        List<Object[]> result=songRepository.playSong(id);
        for(Object[] res:result) {
            res[1]=decompressBytes((byte[]) res[1]);
        }
        return result;
    }

    public Song getSongById(Long id){
        Song song = songRepository.findById(id).get();
        return song;
    }

    public List<Object[]> getNextSong(Long id){
        if(!songRepository.existsById(id)) {
            id=songRepository.findFirstSong();
        }

        List<Object[]> result=songRepository.playNextSong(id);
        for(Object[] res:result) {
            res[2]=decompressBytes((byte[]) res[2]);
            res[3]=decompressBytes((byte[]) res[3]);
        }
        return result;

    }

    public List<Object[]> getPrevSong(Long id){
        if(!songRepository.existsById(id)) {
            id=songRepository.findLastSong();
        }

        List<Object[]> result=songRepository.playNextSong(id);
        for(Object[] res:result) {
            res[2]=decompressBytes((byte[]) res[2]);
            res[3]=decompressBytes((byte[]) res[3]);

        }
        return result;

    }

    public List<Object[]> getLatestSongs(){
        List<Object[]> result=songRepository.findLatestSongs();

        for(Object[] res:result) {
            res[2]=decompressBytes((byte[]) res[2]);
        }
        return result;
    }

    public List<Object[]> getAllLatestSongs(){
        List<Object[]> result=songRepository.findAllLatestSongs();
        for(Object[] res:result) {
            res[2]=decompressBytes((byte[]) res[2]);
        }
        return result;
    }

    public List<Object[]> getCategory(String key){
        List<Object[]> result = songRepository.findCategory(key);
        for(Object[] res:result) {
            res[2]=decompressBytes((byte[]) res[2]);
        }
        return result;
    }

    public List<Object[]> getLanguage(String key){
        List<Object[]> result = songRepository.findLanguage(key);
        for(Object[] res:result) {
            res[2]=decompressBytes((byte[]) res[2]);
        }
        return result;
    }

    public List<Object[]> Search(String key){
        List<Object[]> result = songRepository.searchSong(key);
        for(Object[] res:result) {
            res[2]=decompressBytes((byte[]) res[2]);
        }
        return result;
    }

    public byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];

        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }

        return outputStream.toByteArray();
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];

        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }

        try {
            outputStream.close();
        } catch (IOException e) {
        }

        return outputStream.toByteArray();
    }
}
