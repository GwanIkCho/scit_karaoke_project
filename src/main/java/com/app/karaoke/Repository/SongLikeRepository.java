package com.app.karaoke.Repository;

import com.app.karaoke.Entity.SongLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongLikeRepository extends JpaRepository<SongLikeEntity, Long> {

    @Query("SELECT pl FROM SongLikeEntity pl WHERE pl.userId = :userId AND pl.songId = :songId")
    Optional<SongLikeEntity> findByUserIdAndSongId(@Param("userId") Long userId, @Param("songId") Long songId);
}
