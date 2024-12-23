package com.app.karaoke.Service;

import com.app.karaoke.DTO.UserDTO;
import com.app.karaoke.Entity.UserEntity;
import com.app.karaoke.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // DB에 저장하기 (이미 존재하면 저장 안 할 수도 있음)
    public UserEntity add(UserDTO userDTO) {
        UserEntity userEntity = UserEntity.toEntity(userDTO);
        // save(...) 결과를 리턴
        return userRepository.save(userEntity);
    }

    public UserEntity findByKakaoId(String kakaoId) {
        return userRepository.findByKakaoNumber(kakaoId).orElse(null);
    }

}
