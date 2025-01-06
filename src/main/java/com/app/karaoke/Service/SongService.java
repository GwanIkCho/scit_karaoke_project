package com.app.karaoke.Service;

import com.app.karaoke.DTO.PagenationDTO;
import com.app.karaoke.DTO.SongDTO;
import com.app.karaoke.Entity.SongEntity;
import com.app.karaoke.Repository.PlayListSongRepository;
import com.app.karaoke.Repository.SongRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PlayListSongRepository playListSongRepository;

    public PagenationDTO<SongDTO> searchSongs(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // 띄어쓰기 제거한 키워드로 검색 (REPLACE 방식)
        Page<SongEntity> songPage = songRepository.searchByTitleIgnoringSpaces(keyword, pageable);

        List<SongDTO> content = songPage.getContent()
                .stream()
                .map(SongDTO::toEntity)
                .collect(Collectors.toList());

        return new PagenationDTO<>(
                content,
                songPage.getNumber(),
                songPage.getSize(),
                songPage.getTotalElements(),
                songPage.getTotalPages()
        );
    }


    public List<SongDTO> allSongs() {
        Pageable pageable = PageRequest.of(0, 100);
        List<SongEntity> songs = songRepository.findTopSongs(pageable);

        return songs.stream()
                .map(song -> new SongDTO(song.getId(), song.getTitle(), song.getSinger(), 0L))  // 초기 songCount는 0L
                .collect(Collectors.toList());
    }




}
