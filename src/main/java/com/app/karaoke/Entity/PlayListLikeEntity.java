package com.app.karaoke.Entity;

import com.app.karaoke.DTO.PlayListDTO;
import com.app.karaoke.DTO.PlayListLikeDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tbl_PlayList_Like")
public class PlayListLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_liked")
    private boolean isLiked;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "playList_id")
    private Long playListId;

    @Column(name = "status")
    private int status;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playList_id", insertable = false, updatable = false)  // 외래키와 동일하게 설정
    @ToString.Exclude  // 무한 루프 방지
    private PlayListEntity playlist;

    // 곡 엔티티와의 관계 (읽기 전용)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)  // 외래키와 동일하게 설정
    @ToString.Exclude
    private UserEntity user;

    public static PlayListLikeEntity playListLikeEntity(PlayListLikeDTO playListLikeDTO) {
        LocalDateTime now = LocalDateTime.now();
        return PlayListLikeEntity.builder()
                .id(playListLikeDTO.getId())
                .userId(playListLikeDTO.getUserId())
                .playListId(playListLikeDTO.getPlayListId())
                .isLiked(true)
                .status(1)
                .createTime(now)
                .updateTime(now)
                .build();
    }



}
