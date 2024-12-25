package com.app.karaoke.Controller;

import com.app.karaoke.DTO.PlayListSongDTO;
import com.app.karaoke.Service.PlayListSongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/playList")
@Slf4j
public class PlayListSongAPI {

    @Autowired
    private PlayListSongService playListSongService;

    @GetMapping("/selectAll/API")
    public List<PlayListSongDTO> getPlayListSongs(@RequestParam("id") Long id) {
        log.info(playListSongService.selectAll(id).toString());
        return playListSongService.selectAll(id);
    }


}
