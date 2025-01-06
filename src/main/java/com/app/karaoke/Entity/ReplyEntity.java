package com.app.karaoke.Entity;

import java.time.LocalDateTime;

import com.app.karaoke.DTO.ReplyDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="tbl_reply")
public class ReplyEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long replyId;				// 댓글 고유 번호
	
	@OneToOne
	@JoinColumn(name="user_id")
	private UserEntity userEntity;		// 작성자
	
	@OneToOne
	@JoinColumn(name="song_id")
	private SongEntity songEntity;		// 노래 고유 번호
	
	@Column(name="title")
	private String title;				// 노래제목
	
	@Column(name="status")
	private int status;					// 댓글 상태
	
	@Column(name="create_time")
	private LocalDateTime createTime;	// 작성 시간
	
	@Column(name="update_time")
	private LocalDateTime updateTime;	// 수정 시간
	
//	@Column(name="content")
// 	private String content;				// 내용(DB에 미생성됨)
	
	public static ReplyEntity toEntity(ReplyDTO dto, UserEntity userEntity, SongEntity songEntity) {
	    return ReplyEntity.builder()
	        .replyId(dto.getReplyId())
	        .userEntity(userEntity) // 이미 조회된 UserEntity
	        .songEntity(songEntity) // 이미 조회된 SongEntity
	        .title(dto.getTitle())
	        .status(dto.getStatus())
	        .createTime(dto.getCreateTime())
	        .updateTime(dto.getUpdateTime())
	        .build();
				
	}
}
