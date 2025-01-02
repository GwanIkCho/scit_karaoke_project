package com.app.karaoke.Entity;

import com.app.karaoke.DTO.SongDTO;
import com.app.karaoke.DTO.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="tbl_song")
public class SongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 생성 전략
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "singer")
    private String singer;

    @Column(name = "tj_number")
    private String tjNumber;

    @Column(name = "ky_number")
    private String kyNumber;

    @Column(name = "status")
    private int status;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "song")
    private List<PlayListSongEntity> playListSongs;  // 플레이리스트와의 관계 설정

    public static SongEntity toEntity(SongDTO songDTO) {
        return SongEntity.builder()
                .id(songDTO.getId())
                .title(songDTO.getTitle())
                .singer(songDTO.getSinger())
                .tjNumber(songDTO.getTjNumber())
                .kyNumber(songDTO.getKyNumber())
                .build();
    }


}
