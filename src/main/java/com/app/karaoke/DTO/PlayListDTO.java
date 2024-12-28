package com.app.karaoke.DTO;

import com.app.karaoke.Entity.PlayListEntity;
import com.app.karaoke.Entity.PlayListLikeEntity;
import com.app.karaoke.Entity.SongEntity;
import com.app.karaoke.Entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PlayListDTO {

    private Long id;  // 플레이리스트 ID
    private String playListName;
    private Long userId;  // 사용자 ID
    private int status;  // 상태값
    private LocalDateTime createTime;  // 생성 시간
    private LocalDateTime updateTime;  // 업데이트 시간


    private PlayListLikeEntity playListLike;  // null 가능
    private boolean isLiked;

    public PlayListDTO(Long id, String playListName, Long userId, int status, LocalDateTime createTime, LocalDateTime updateTime, boolean isLiked) {
        this.id = id;
        this.playListName = playListName;
        this.userId = userId;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isLiked = isLiked;
    }

    public static PlayListDTO playlisttoDTO(PlayListEntity playListEntity) {
        return PlayListDTO.builder()
                .id(playListEntity.getId())
                .userId(playListEntity.getUserId())
                .playListName(playListEntity.getPlayListName())
                .createTime(playListEntity.getCreateTime())// Entity의 필드를 DTO로 매핑
                .build();
    }

}
