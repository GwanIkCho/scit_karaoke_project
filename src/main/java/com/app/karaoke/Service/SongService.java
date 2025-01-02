package com.app.karaoke.Service;

import com.app.karaoke.DTO.PagenationDTO;
import com.app.karaoke.DTO.SongDTO;
import com.app.karaoke.Entity.SongEntity;
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

    public PagenationDTO<SongDTO> searchSongs(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);  // 페이지네이션 정보 설정

        Page<SongEntity> songPage = songRepository.findByTitleContaining(keyword, pageable);

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

}
