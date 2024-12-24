package com.app.karaoke.Service;

import com.app.karaoke.DTO.UserDTO;
import com.app.karaoke.Entity.PlayListEntity;
import com.app.karaoke.Repository.PlayListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayListService {

    @Autowired
    private PlayListRepository playListRepository;




}
