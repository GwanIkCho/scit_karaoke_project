package com.app.karaoke.Entity;


import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.app.karaoke.DTO.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name="tbl_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 생성 전략
    private Long id;
    @Column(name="user_name")
    private String userName;
    @Column(name="user_email")
    private String userEmail;
    @Column(name="kakao_number")
    private String kakaoNumber;

    @OneToMany(mappedBy = "user")
    private List<PlayListEntity> playLists;  // 플레이리스트와의 관계 설정

    @OneToMany(mappedBy = "user")
    private List<PlayListLikeEntity> playListLikes;  // 플레이리스트와의 관계 설정

    @OneToMany(mappedBy = "user")
    private List<SongLikeEntity> songLikes;  // 플레이리스트와의 관계 설정
    
    @Column(name="create_time")
    @CreationTimestamp
    private LocalDateTime createTime;
    
    @Column(name="status")
    @Builder.Default
    private int status = 1;

    public static UserEntity toEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .id(userDTO.getId())
                .userName(userDTO.getUserName())
                .userEmail(userDTO.getUserEmail())
                .kakaoNumber(userDTO.getKakaoNumber())
                .createTime(userDTO.getCreateTime())
                .status(userDTO.getStatus())
                .build();
    }
}
