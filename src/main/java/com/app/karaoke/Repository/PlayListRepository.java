package com.app.karaoke.Repository;

import com.app.karaoke.Entity.PlayListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayListRepository extends JpaRepository<PlayListEntity, Long> {
}
