package com.app.karaoke.Controller;

import com.app.karaoke.DTO.PlayListDTO;
import com.app.karaoke.DTO.SongDTO;
import com.app.karaoke.Service.PlayListService;
import com.app.karaoke.Service.PlayListSongService;
import com.app.karaoke.Service.SongLikeService;
import com.app.karaoke.Service.SongService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class RankingAPI {

    @Autowired
    private SongService songService;

    @Autowired
    private SongLikeService songLikeService;

    @Autowired
    private PlayListService playListService;

    @Autowired
    private PlayListSongService playListSongService;

    @GetMapping("/rank/all")
    public List<SongDTO> rankAll() {
        List<SongDTO> list = songService.allSongs();

        return list;
    }

    @GetMapping("/rank/likeCheck")
    public ResponseEntity<Void> likeCheck(@RequestParam(name = "id") Long playListId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUserId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  // 401 상태 반환
        }

        Long userId = (Long) session.getAttribute("loginUserId");
        songLikeService.like(userId, playListId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/rank/playList")
    public ResponseEntity<List<PlayListDTO>> findByUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUserId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  // 401 상태 반환
        }

        Long userId = (Long) session.getAttribute("loginUserId");
        List<PlayListDTO> playList = playListService.findByUserId(userId);
        log.info(playList.toString());

        return ResponseEntity.ok(playList);
    }

    @PostMapping("/rank/addplaylist")
    public ResponseEntity<?> addSongToPlayList(@RequestBody Map<String, Long> requestData) {
        Long playListId = requestData.get("playListId");
        Long songId = requestData.get("songId");

        try {
            playListSongService.add(playListId, songId);
            return ResponseEntity.ok("플레이리스트에 추가되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }



}
