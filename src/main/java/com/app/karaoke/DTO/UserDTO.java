package com.app.karaoke.DTO;

import com.app.karaoke.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
    private Long id;
    private String userName;
    private String userEmail;
    private String kakaoNumber;

    public static UserDTO toDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getId())
                .userName(userEntity.getUserName())
                .userEmail(userEntity.getUserEmail())
                .kakaoNumber(userEntity.getKakaoNumber())
                .build();
    }
}
