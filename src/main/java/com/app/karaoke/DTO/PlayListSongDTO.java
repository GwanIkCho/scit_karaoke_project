package com.app.karaoke.DTO;

import com.app.karaoke.Entity.PlayListEntity;
import com.app.karaoke.Entity.PlayListSongEntity;
import com.app.karaoke.Entity.SongEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PlayListSongDTO {

    private Long id;
    private Long playListId;
    private Long songId;
    private int status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private PlayListEntity playlist;  // PlayListEntity 직접 포함
    private SongEntity song;          // SongEntity 직접 포함


    // playlistsong용
    private String title;
    private String singer;
    private String tjNumber;
    private String kyNumber;
    private String playListName;  // PlayList 이름

    public PlayListSongDTO(Long id, Long playListId, Long songId, int status,
                           PlayListEntity playlist, SongEntity song) {
        this.id = id;
        this.playListId = playListId;
        this.songId = songId;
        this.status = status;
        this.tjNumber = song.getTjNumber();
        this.kyNumber = song.getKyNumber();
        this.createTime = playlist.getCreateTime();  // PlayListEntity의 createTime
        this.updateTime = playlist.getUpdateTime();  // PlayListEntity의 updateTime
        this.playListName = playlist.getPlayListName();
        this.title = song.getTitle();
        this.singer = song.getSinger();
    }

    public static PlayListSongDTO toEntity(PlayListSongEntity playListSongEntity) {
        return PlayListSongDTO.builder()
                .id(playListSongEntity.getId())
                .playListId(playListSongEntity.getPlayListId())
                .songId(playListSongEntity.getSongId())
                .status(1)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
    }

}
