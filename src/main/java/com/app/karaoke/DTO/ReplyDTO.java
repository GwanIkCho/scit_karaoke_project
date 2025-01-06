package com.app.karaoke.DTO;

import java.time.LocalDateTime;

import com.app.karaoke.Entity.ReplyEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReplyDTO {
	
	private Long replyId;				// 댓글 고유 번호
	private Long userId;				// 작성자
	private Long songId;				// 노래 고유 번호
	private String title;				// 노래제목
	private int status;					// 상태
	private LocalDateTime createTime;	// 작성 시간
	private LocalDateTime updateTime;	// 수정 시간
// 	private String content;				// 내용(DB에 미생성됨)
	
	public static ReplyDTO toDTO(ReplyEntity replyEntity) {
		return ReplyDTO.builder()
				.replyId(replyEntity.getReplyId())
				.userId(replyEntity.getUserEntity().getId())
				.songId(replyEntity.getSongEntity().getId())
				.title(replyEntity.getTitle())
				.status(replyEntity.getStatus())
				.createTime(replyEntity.getCreateTime())
				.updateTime(replyEntity.getUpdateTime())
				.build();
	}
}
