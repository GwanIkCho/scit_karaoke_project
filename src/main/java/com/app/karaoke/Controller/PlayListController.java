package com.app.karaoke.Controller;

import com.app.karaoke.DTO.PlayListDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/playList")
@Slf4j
public class PlayListController {

    @GetMapping("/playlist")
    public String playList(){
        return "/playList/playlist";
    }

    @GetMapping("/search")
    public String searsh(){
        return "/playList/search";
    }

}
