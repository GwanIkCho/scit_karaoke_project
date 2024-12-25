package com.app.karaoke.Entity;

import com.app.karaoke.DTO.PlayListSongDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tbl_PlayList_Song")
public class PlayListSongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "playList_id")
    private Long playListId;  // 외래키 필드

    @Column(name = "song_id")
    private Long songId;  // 외래키 필드

    @Column(name = "status")
    private int status;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    // 플레이리스트 엔티티와의 관계 (읽기 전용)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playList_id", insertable = false, updatable = false)  // 외래키와 동일하게 설정
    @ToString.Exclude  // 무한 루프 방지
    private PlayListEntity playlist;

    // 곡 엔티티와의 관계 (읽기 전용)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", insertable = false, updatable = false)  // 외래키와 동일하게 설정
    @ToString.Exclude
    private SongEntity song;

    // DTO -> Entity 변환 메서드 (중복 시간 호출 방지)
    public static PlayListSongEntity toEntity(PlayListSongDTO playListSongDTO) {
        LocalDateTime now = LocalDateTime.now();  // 한 번만 호출
        return PlayListSongEntity.builder()
                .id(playListSongDTO.getId())
                .playListId(playListSongDTO.getPlayListId())
                .songId(playListSongDTO.getSongId())
                .status(1)
                .createTime(now)
                .updateTime(now)
                .build();
    }
}
