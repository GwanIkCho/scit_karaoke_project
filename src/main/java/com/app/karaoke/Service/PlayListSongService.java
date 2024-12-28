package com.app.karaoke.Service;

import com.app.karaoke.DTO.PlayListDTO;
import com.app.karaoke.DTO.PlayListSongDTO;
import com.app.karaoke.Entity.PlayListEntity;
import com.app.karaoke.Entity.PlayListSongEntity;
import com.app.karaoke.Repository.PlayListRepository;
import com.app.karaoke.Repository.PlayListSongRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlayListSongService {

    @Autowired
    private PlayListSongRepository playListSongRepository;

    @Autowired
    private PlayListRepository playListRepository;


    public List<PlayListSongDTO> selectAll(Long playListId) {
        return playListSongRepository.findAllByPlayListId(playListId);
    }

    @Transactional
    public void songsoftDelete(Long playListId) {
        PlayListSongEntity target = playListSongRepository.findById(playListId)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found"));
        target.setStatus(0);  // status를 0으로 변경
        playListSongRepository.save(target);

    }

    public PlayListDTO selectById(Long playListId) {
        PlayListEntity target = playListRepository.findById(playListId)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found"));


        return PlayListDTO.playlisttoDTO(target);  // 엔티티 → DTO 변환
    }


}
