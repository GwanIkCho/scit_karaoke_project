package com.app.karaoke.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RankingController {

    @GetMapping("/rank")
    public String rank() {
        return "rank";
    }



}
