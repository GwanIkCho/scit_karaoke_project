package com.app.karaoke.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/playList")
public class PlayListController {

    @GetMapping("/all")
    public String playList(){
        return "/playList/all";
    }


}
