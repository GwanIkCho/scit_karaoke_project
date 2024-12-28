package com.app.karaoke.DTO;

import com.app.karaoke.Entity.PlayListLikeEntity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PlayListLikeDTO {

    private Long id;
    private boolean isLiked;
    private Long userId;
    private Long playListId;
    private int status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static PlayListLikeDTO playListLikeDTO(PlayListLikeEntity playListLikeEntity) {
        LocalDateTime now = LocalDateTime.now();
        return PlayListLikeDTO.builder()
                .id(playListLikeEntity.getId())
                .userId(playListLikeEntity.getUserId())
                .playListId(playListLikeEntity.getPlayListId())
                .isLiked(true)
                .status(1)
                .createTime(now)
                .updateTime(now)
                .build();
    }
}
