package com.app.karaoke.Service;


import com.app.karaoke.Entity.PlayListLikeEntity;
import com.app.karaoke.Repository.PlayListLikeRepository;
import com.app.karaoke.Repository.PlayListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlayListLikeService {

    @Autowired
    private PlayListLikeRepository playListLikeRepository;
    @Autowired
    private PlayListRepository playListRepository;


    @Transactional
    public void like(Long userId, Long playListId) {
        Optional<PlayListLikeEntity> optional = playListLikeRepository.findByUserIdAndPlayListId(userId, playListId);

        PlayListLikeEntity playListLikeEntity = optional.orElseGet(() -> PlayListLikeEntity.builder()
                .userId(userId)
                .playListId(playListId)
                .isLiked(false)  // 처음엔 좋아요 해제 상태로
                .status(1)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build());

        // 상태 토글
        playListLikeEntity.setLiked(!playListLikeEntity.isLiked());
        playListLikeEntity.setUpdateTime(LocalDateTime.now());

        // 새로 생성된 엔티티만 저장 (영속 상태일 경우, save 불필요)
        if (!optional.isPresent()) {
            playListLikeRepository.save(playListLikeEntity);
        }
    }




}
