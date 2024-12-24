package com.app.karaoke.Service;

import com.app.karaoke.DTO.UserDTO;
import com.app.karaoke.Entity.PlayListEntity;
import com.app.karaoke.Entity.UserEntity;
import com.app.karaoke.Repository.PlayListRepository;
import com.app.karaoke.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlayListRepository playListRepository;

    // DB에 저장하기 (이미 존재하면 저장 안 할 수도 있음)
    @Transactional
    public UserEntity add(UserDTO userDTO) {
        UserEntity userEntity = UserEntity.toEntity(userDTO);
        UserEntity savedUser = userRepository.save(userEntity);
        userRepository.flush();  // 사용자 즉시 DB 반영


        PlayListEntity playListEntity = PlayListEntity.builder()
                .userId(savedUser.getId())
                .playListName("좋아요 플레이리스트")
                .status(1)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        playListRepository.save(playListEntity);
        playListRepository.flush();  // 플레이리스트도 강제로 DB 반영


        return savedUser;
    }









    public UserEntity findByKakaoId(String kakaoId) {
        return userRepository.findByKakaoNumber(kakaoId).orElse(null);
    }

}
