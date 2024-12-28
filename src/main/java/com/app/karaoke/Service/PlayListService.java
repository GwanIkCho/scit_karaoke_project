package com.app.karaoke.Service;

import com.app.karaoke.DTO.PlayListDTO;
import com.app.karaoke.DTO.UserDTO;
import com.app.karaoke.Entity.PlayListEntity;
import com.app.karaoke.Repository.PlayListRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PlayListService {

    @Autowired
    private PlayListRepository playListRepository;


    public List<PlayListDTO> findByUserId(Long userId) {
        return playListRepository.findByUserIdWithLikes(userId)
                .stream()
                .filter(dto -> dto.getStatus() == 1) // 상태가 1인 것만 필터링
                .collect(Collectors.toList());
    }

    public void add(PlayListDTO playListDTO) {
        PlayListEntity playListEntity = PlayListEntity.playlisttoEntity(playListDTO);
        playListRepository.save(playListEntity);
    }

    @Transactional
    public void softDelete(Long playListId) {
        PlayListEntity target = playListRepository.findById(playListId)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found"));
        target.setStatus(0);  // status를 0으로 변경
        playListRepository.save(target);

    }

    public void changname(PlayListDTO playListDTO) {
        PlayListEntity target = playListRepository.findById(playListDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found"));
        target.setPlayListName(playListDTO.getPlayListName());
        playListRepository.save(target);
    }


}
