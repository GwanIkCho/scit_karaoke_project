package com.app.karaoke.Entity;

import com.app.karaoke.DTO.PlayListDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tbl_PlayList")
public class PlayListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "playList_name")
    private String playListName = "좋아요 플레이리스트";

    @Column(name = "user_id", nullable = false)
    private Long userId;  // 외래키 필드

    @Column(name = "status")
    private int status;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "playlist")
    private List<PlayListLikeEntity> playListLikes;  // 플레이리스트와의 관계 설정

    // 플레이리스트와 곡 연결 (양방향 매핑)
    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude  // 무한 루프 방지
    private List<PlayListSongEntity> playListSongs;

    // 사용자 엔티티와의 관계 (읽기 전용)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)  // 읽기 전용 설정
    @ToString.Exclude
    private UserEntity user;

    // DTO -> Entity 변환 메서드
    public static PlayListEntity playlisttoEntity(PlayListDTO playListDTO) {
        LocalDateTime now = LocalDateTime.now();
        return PlayListEntity.builder()
                .id(playListDTO.getId())
                .playListName(
                        playListDTO.getPlayListName() != null ? playListDTO.getPlayListName() : "좋아요 플레이리스트"
                )
                .userId(playListDTO.getUserId())
                .status(1)
                .createTime(now)
                .updateTime(now)
                .build();
    }
}
