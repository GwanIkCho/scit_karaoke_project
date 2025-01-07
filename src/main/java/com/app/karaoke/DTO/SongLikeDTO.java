package com.app.karaoke.DTO;

import com.app.karaoke.Entity.SongEntity;
import com.app.karaoke.Entity.SongLikeEntity;
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
public class SongLikeDTO {

    private Long id;
    private boolean isLiked;
    private Long userId;
    private Long songId;
    private int status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


    public static SongLikeDTO toDTO(SongLikeEntity songLikeEntity) {
        return SongLikeDTO.builder()
                .id(songLikeEntity.getId())
                .isLiked(true)
                .userId(songLikeEntity.getUserId())
                .songId(songLikeEntity.getSongId())
                .status(songLikeEntity.getStatus())
                .createTime(songLikeEntity.getCreateTime())
                .updateTime(songLikeEntity.getUpdateTime())
                .build();
    }


}
