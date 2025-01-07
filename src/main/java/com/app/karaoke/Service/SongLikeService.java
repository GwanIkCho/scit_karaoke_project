package com.app.karaoke.Service;

import com.app.karaoke.Entity.SongLikeEntity;
import com.app.karaoke.Repository.SongLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SongLikeService {

    @Autowired
    private SongLikeRepository songLikeRepository;

    @Transactional
    public void like(Long userId, Long playListId) {
        Optional<SongLikeEntity> optional = songLikeRepository.findByUserIdAndSongId(userId, playListId);

        SongLikeEntity songLikeEntity = optional.orElseGet(() -> SongLikeEntity.builder()
                .userId(userId)
                .songId(playListId)
                .isLiked(true)  // 처음엔 좋아요 상태로 설정
                .status(1)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build());

        // 상태 토글
        if (optional.isPresent()) {
            songLikeEntity.setLiked(!songLikeEntity.isLiked());
            songLikeEntity.setUpdateTime(LocalDateTime.now());
        }

        // 새로 생성된 엔티티만 저장 (영속 상태일 경우, save 불필요)
        if (!optional.isPresent()) {
            songLikeRepository.save(songLikeEntity);
        }
    }


}
