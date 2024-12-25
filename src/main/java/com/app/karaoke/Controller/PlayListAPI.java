package com.app.karaoke.Controller;

import com.app.karaoke.DTO.PlayListDTO;
import com.app.karaoke.Service.PlayListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playList")
@Slf4j
public class PlayListAPI {

    @Autowired
    private PlayListService playListService;

    @GetMapping("/selectAll")
    public List<PlayListDTO> selectAll(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션 가져오기

        Long loginUserId = (Long) session.getAttribute("loginUserId");
        List<PlayListDTO> list = playListService.findByUserId(loginUserId);
        return  list;
    }

    @PostMapping("/add")
    public void add(@RequestBody PlayListDTO playListDTO, HttpServletRequest request){
        HttpSession session = request.getSession(false); // 세션 가져오기

        Long loginUserId = (Long) session.getAttribute("loginUserId");
        playListDTO.setUserId(loginUserId);
        playListService.add(playListDTO);

    }

    @GetMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam(name = "id") Long id) {
        playListService.softDelete(id);
        return ResponseEntity.ok("Playlist status updated to 0.");
    }

    @PostMapping("/namech")
    public void namech(@RequestBody PlayListDTO playListDTO){
        playListService.changname(playListDTO);
    }



}
