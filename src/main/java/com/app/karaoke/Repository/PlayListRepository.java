package com.app.karaoke.Repository;

import com.app.karaoke.DTO.PlayListDTO;
import com.app.karaoke.Entity.PlayListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayListRepository extends JpaRepository<PlayListEntity, Long> {

    @Query("SELECT new com.app.karaoke.DTO.PlayListDTO(ps.id, ps.playListName, ps.userId, ps.status, ps.createTime, ps.updateTime, COALESCE(pl.isLiked, false)) " +
            "FROM PlayListEntity ps " +
            "LEFT JOIN PlayListLikeEntity pl ON ps.id = pl.playListId AND pl.userId = :userId " +
            "WHERE ps.userId = :userId")
    List<PlayListDTO> findByUserIdWithLikes(@Param("userId") Long userId);


    List<PlayListEntity> findByUserId(Long userId);
    Optional<PlayListEntity> findById(Long id);
}
