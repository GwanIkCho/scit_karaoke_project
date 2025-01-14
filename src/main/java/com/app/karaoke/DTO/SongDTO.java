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
public class SongDTO {

    private Long id;
    private String title;
    private String singer;
    private String tjNumber;
    private String kyNumber;
    private int status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private Long songCount;
    private SongLikeEntity songLike;


    private boolean isLiked;

    // 필요한 생성자 추가
    public SongDTO(Long id, String title, String singer, Long songCount, boolean isLiked) {
        this.id = id;
        this.title = title;
        this.singer = singer;
        this.songCount = songCount;
        this.isLiked = isLiked;
    }


    public static SongDTO toDTO(SongEntity songEntity) {
        return SongDTO.builder()
                .id(songEntity.getId())
                .title(songEntity.getTitle())
                .singer(songEntity.getSinger())
                .tjNumber(songEntity.getTjNumber())
                .kyNumber(songEntity.getKyNumber())
                .build();
    }


}
