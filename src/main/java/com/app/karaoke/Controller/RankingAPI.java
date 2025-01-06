package com.app.karaoke.Controller;

import com.app.karaoke.DTO.SongDTO;
import com.app.karaoke.Service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class RankingAPI {

    @Autowired
    private SongService songService;

    @GetMapping("/rank/all")
    public List<SongDTO> rankAll() {
        List<SongDTO> list = songService.allSongs();

        log.info(list.toString());
        return list;
    }
}
