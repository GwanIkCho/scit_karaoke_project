package com.app.karaoke.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/adminUser")
    public String adminUser() {
        return "admin/adminUser";
    }
    
    @GetMapping("/adminSong")
    public String adminSong() {
        return "admin/adminSong";
    }
    
    @GetMapping("/adminReply")
    public String adminReply() {
        return "admin/adminReply";
    }    
}
