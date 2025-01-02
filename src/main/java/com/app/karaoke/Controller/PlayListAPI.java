package com.app.karaoke.Controller;

import com.app.karaoke.DTO.PagenationDTO;
import com.app.karaoke.DTO.PlayListDTO;
import com.app.karaoke.DTO.SongDTO;
import com.app.karaoke.Entity.SongEntity;
import com.app.karaoke.Repository.PlayListLikeRepository;
import com.app.karaoke.Repository.SongRepository;
import com.app.karaoke.Service.PlayListLikeService;
import com.app.karaoke.Service.PlayListService;
import com.app.karaoke.Service.SongService;
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

    @Autowired
    private PlayListLikeService playListLikeService;

    @Autowired
    private SongService songService;


    @GetMapping("/selectAll")
    public List<PlayListDTO> selectAll(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션 가져오기

        Long loginUserId = (Long) session.getAttribute("loginUserId");
        List<PlayListDTO> list = playListService.findByUserId(loginUserId);
        log.info(list.toString());
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


    @GetMapping("/likeCheck")
    public ResponseEntity<String> likeCheck(@RequestParam(name = "id") Long playListId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);  // 세션 가져오기
        if (session == null || session.getAttribute("loginUserId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Long userId = (Long) session.getAttribute("loginUserId");  // 세션에서 유저 ID 가져오기

        playListLikeService.like(userId, playListId);  // 좋아요 토글 메서드 호출

        return ResponseEntity.ok("좋아요 상태가 변경되었습니다.");
    }

    @GetMapping("/searchAPI")
    public PagenationDTO<SongDTO> selectAllSearch(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return songService.searchSongs(keyword, page, size);
    }




}
