package com.app.karaoke.Entity;


import com.app.karaoke.DTO.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    public static UserEntity toEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .id(userDTO.getId())
                .userName(userDTO.getUserName())
                .userEmail(userDTO.getUserEmail())
                .kakaoNumber(userDTO.getKakaoNumber())
                .build();
    }
}
