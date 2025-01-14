package com.app.karaoke.Service;

import com.app.karaoke.DTO.PagenationDTO;
import com.app.karaoke.DTO.SongDTO;
import com.app.karaoke.DTO.UserDTO;
import com.app.karaoke.Entity.PlayListLikeEntity;
import com.app.karaoke.Entity.SongEntity;
import com.app.karaoke.Entity.SongLikeEntity;
import com.app.karaoke.Entity.UserEntity;
import com.app.karaoke.Repository.PlayListSongRepository;
import com.app.karaoke.Repository.SongRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
                .map(SongDTO::toDTO)
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
        return songRepository.findTopSongsWithLikeStatus(pageable);
    }


	public List<SongDTO> songAll() {
		
		List<SongEntity> temp = songRepository.findAll();
		
		List<SongDTO> list = new ArrayList<>();
		
		temp.forEach((entity) -> list.add(SongDTO.toDTO(entity)));
	
		return list;
	}




}
