package com.app.karaoke.Repository;

import com.app.karaoke.DTO.SongDTO;
import com.app.karaoke.Entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long> {


    @Query(value = "SELECT * FROM tbl_song WHERE REPLACE(title, ' ', '') LIKE CONCAT('%', REPLACE(:keyword, ' ', ''), '%')",
            countQuery = "SELECT COUNT(*) FROM tbl_song WHERE REPLACE(title, ' ', '') LIKE CONCAT('%', REPLACE(:keyword, ' ', ''), '%')",
            nativeQuery = true)
    Page<SongEntity> searchByTitleIgnoringSpaces(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT s FROM PlayListSongEntity ps " +
            "JOIN ps.song s " +
            "GROUP BY s " +
            "ORDER BY COUNT(ps.song.id) DESC")
    List<SongEntity> findTopSongs(Pageable pageable);

}