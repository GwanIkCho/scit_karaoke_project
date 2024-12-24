package com.app.karaoke.Entity;

import com.app.karaoke.DTO.PlayListDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

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
    private Long userId;  // 직접 삽입할 user_id

    @Column(name = "status")
    private int status;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    // UserEntity는 조회용으로만 사용
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)  // 읽기 전용
    private UserEntity user;

    public static PlayListEntity playlisttoEntity(PlayListDTO playListDTO) {
        return PlayListEntity.builder()
                .id(playListDTO.getId())
                .playListName(
                        playListDTO.getPlayListName() != null ? playListDTO.getPlayListName() : "좋아요 플레이리스트"
                )
                .userId(playListDTO.getUserId())
                .build();
    }
}
