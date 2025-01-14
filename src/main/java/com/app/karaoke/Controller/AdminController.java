package com.app.karaoke.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.karaoke.DTO.SongDTO;
import com.app.karaoke.DTO.UserDTO;
import com.app.karaoke.Service.ReplyService;
import com.app.karaoke.Service.SongService;
import com.app.karaoke.Service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	
	private final UserService userService;
	private final SongService songService;
	private final ReplyService replyService;
	
	// url이동
    @GetMapping("/adminUser")
    public String adminUser(Model model) {
    	
    	List<UserDTO> list = userService.userAll();
    	
    	model.addAttribute("list", list);
    	
    	// html페이지 명 
        return "admin/adminUser";
    }
    
    // url이동
    @GetMapping("/adminSong")
    public String adminSong(Model model) {
    	
    	List<SongDTO> list = songService.songAll();
    	
    	model.addAttribute("list", list);
    	
    	// html페이지 명 
        return "admin/adminSong";
    }
    
    // url이동
    @GetMapping("/adminReply")
    public String adminReply() {
        
    	// html페이지 명 
    	return "admin/adminReply";
    }    
    
}
