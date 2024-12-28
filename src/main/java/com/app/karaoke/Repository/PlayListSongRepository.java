package com.app.karaoke.Repository;

import com.app.karaoke.DTO.PlayListSongDTO;
import com.app.karaoke.Entity.PlayListEntity;
import com.app.karaoke.Entity.PlayListSongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayListSongRepository extends JpaRepository<PlayListSongEntity, Long> {

    @Query("SELECT new com.app.karaoke.DTO.PlayListSongDTO(ps.id, ps.playListId, s.id, ps.status, pl, s) " +
            "FROM PlayListSongEntity ps " +
            "JOIN ps.playlist pl " +
            "JOIN ps.song s " +
            "WHERE ps.playListId = :playListId AND ps.status = 1")
    List<PlayListSongDTO> findAllByPlayListId(@Param("playListId") Long playListId);


    Optional<PlayListSongEntity> findById(Long id);

}
