package com.app.karaoke.Repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.karaoke.Entity.ReplyEntity;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

	List<ReplyEntity> findByReplyIdContains(String searchWord, Sort by);

	List<ReplyEntity> findByCreateTimeContains(String searchWord, Sort by);

	//List<ReplyEntity> findByreplyLikeContains(String searchWord, Sort by);

//	 List<ReplyEntity> findByContentContaining(String query);

}
