package com.app.karaoke.Repository;

import com.app.karaoke.Entity.PlayListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayListRepository extends JpaRepository<PlayListEntity, Long> {
    List<PlayListEntity> findByUserId(Long userId);
    Optional<PlayListEntity> findById(Long id);
}
