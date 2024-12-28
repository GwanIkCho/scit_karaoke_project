package com.app.karaoke.Repository;

import com.app.karaoke.Entity.PlayListLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayListLikeRepository extends JpaRepository<PlayListLikeEntity, Long> {
    @Query("SELECT pl FROM PlayListLikeEntity pl WHERE pl.userId = :userId AND pl.playListId = :playListId")
    Optional<PlayListLikeEntity> findByUserIdAndPlayListId(@Param("userId") Long userId, @Param("playListId") Long playListId);


}
