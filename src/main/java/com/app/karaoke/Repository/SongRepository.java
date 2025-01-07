package com.app.karaoke.Repository;

import com.app.karaoke.DTO.SongDTO;
import com.app.karaoke.Entity.PlayListLikeEntity;
import com.app.karaoke.Entity.SongEntity;
import com.app.karaoke.Entity.SongLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long> {


    @Query(value = "SELECT * FROM tbl_song WHERE REPLACE(title, ' ', '') LIKE CONCAT('%', REPLACE(:keyword, ' ', ''), '%')",
            countQuery = "SELECT COUNT(*) FROM tbl_song WHERE REPLACE(title, ' ', '') LIKE CONCAT('%', REPLACE(:keyword, ' ', ''), '%')",
            nativeQuery = true)
    Page<SongEntity> searchByTitleIgnoringSpaces(@Param("keyword") String keyword, Pageable pageable);


    @Query("SELECT new com.app.karaoke.DTO.SongDTO(s.id, s.title, s.singer, COUNT(ps.song.id), " +
            "CASE WHEN sl.isLiked IS NULL THEN false ELSE sl.isLiked END) " +
            "FROM PlayListSongEntity ps " +
            "JOIN ps.song s " +
            "LEFT JOIN s.songLikes sl " +
            "GROUP BY s.id, sl.isLiked " +
            "ORDER BY COUNT(ps.song.id) DESC")
    List<SongDTO> findTopSongsWithLikeStatus(Pageable pageable);




}