package com.app.karaoke.Entity;

import com.app.karaoke.DTO.SongDTO;
import com.app.karaoke.DTO.SongLikeDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="tbl_Song_Like")
public class SongLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 생성 전략
    private Long id;

    @Column(name="is_liked")
    private boolean isLiked;

    @Column(name="user_id")
    private Long userId;

    @Column(name="song_id")
    private Long songId;

    @Column(name="status")
    private int status;

    @Column(name="create_time")
    private LocalDateTime createTime;

    @Column(name="update_time")
    private LocalDateTime updateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)  // 외래키와 동일하게 설정
    @ToString.Exclude
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", insertable = false, updatable = false)  // 외래키와 동일하게 설정
    @ToString.Exclude  // 무한 루프 방지
    private SongEntity song;

    // 곡 엔티티와의 관계 (읽기 전용)


    public static SongLikeEntity toEntity(SongLikeDTO songLikeDTO) {
        return SongLikeEntity.builder()
                .id(songLikeDTO.getId())
                .isLiked(true)
                .userId(songLikeDTO.getUserId())
                .songId(songLikeDTO.getSongId())
                .status(songLikeDTO.getStatus())
                .createTime(songLikeDTO.getCreateTime())
                .updateTime(songLikeDTO.getUpdateTime())
                .build();
    }

}
