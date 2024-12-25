package com.app.karaoke.Controller;

import com.app.karaoke.DTO.PlayListSongDTO;
import com.app.karaoke.Entity.PlayListSongEntity;
import com.app.karaoke.Service.PlayListSongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/playList")
@Slf4j
public class PlayListSongController {

    @Autowired
    private PlayListSongService playListSongService;

    @GetMapping("/detail")
    public String write(@RequestParam("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "/playList/detail";
    }

}
