package com.app.karaoke.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.karaoke.DTO.ReplyDTO;
import com.app.karaoke.Entity.ReplyEntity;
import com.app.karaoke.Repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ReplyService {
	
	private final ReplyRepository repository;
	
	//1. 댓글 검색
	public List<ReplyDTO> selectAll(String searchItem, String searchWord) {
			// 1. 단순조회
			//	List<BoardEntity> entityList = repository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));

			// 2. 검색기능 추가(쿼리메소드 생성)
			// 		아무것도 검색하지 않을 시 전체 검색 -> select * from board where 변수 = '%%';
			List<ReplyEntity> temp = null;
			
			switch (searchItem) {
				//	글 쓴 사람으로 조회 -> ex) select * from board where boardWriter = '%길동%';
				case "userId" 	: 
					temp = repository.findByReplyIdContains(searchWord, Sort.by(Sort.Direction.DESC, "userId"));
					break;
				//	글 제목으로 조회 -> ex) select * from board where boardTitle = '%점메추%';
				case "createDate" 	: 
					temp = repository.findByCreateTimeContains(searchWord, Sort.by(Sort.Direction.DESC, "createTime"));
					break;
				//	글 내용으로 조회 -> ex) select * from board where boardContent= '%개추%';
//				case "boardContent" : 
//					temp = repository.findByreplyLikeContains(searchWord, Sort.by(Sort.Direction.DESC, "replyLike"));
//					break;
				default :
					temp = repository.findAll(Sort.by(Sort.Direction.DESC, "createTime"));
					break;
			}
			
			List<ReplyDTO> dtoList = new ArrayList<>();
			temp.forEach((entity) -> dtoList.add(ReplyDTO.toDTO(entity)));
			
			return dtoList;
	}
	
	//검색창 구현
//	  public List<ReplyDTO> searchReplies(String query) {
//		  List<ReplyEntity> temp = repository.findByContentContaining(query);
//		  if(temp.isEmpty()) {
//			 return null;
//		  } else {
//			 List<ReplyDTO> dtoList = new ArrayList<>();
//			 temp.forEach((entity) -> dtoList.add(ReplyDTO.toDTO(entity)));
//
//			return dtoList;
//		  }
//	  }
}
