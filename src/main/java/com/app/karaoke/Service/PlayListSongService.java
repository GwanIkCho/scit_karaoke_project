package com.app.karaoke.Service;

import com.app.karaoke.DTO.PlayListDTO;
import com.app.karaoke.DTO.PlayListSongDTO;
import com.app.karaoke.Entity.PlayListEntity;
import com.app.karaoke.Entity.PlayListSongEntity;
import com.app.karaoke.Repository.PlayListSongRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlayListSongService {

    @Autowired
    private PlayListSongRepository playListSongRepository;


    public List<PlayListSongDTO> selectAll(Long playListId) {
        return playListSongRepository.findAllByPlayListId(playListId);
    }



}
