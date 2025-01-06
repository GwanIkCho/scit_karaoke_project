package com.app.karaoke.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.karaoke.DTO.ReplyDTO;
import com.app.karaoke.DTO.SongDTO;
import com.app.karaoke.Service.ReplyService;
import com.app.karaoke.Service.SongService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/song")
@RequiredArgsConstructor
@Slf4j
public class SongController {

	private final SongService songService;
	private final ReplyService replyService;
	
	//1. 값 가져오기
    @GetMapping("/songInfo")
    public String songInfo(
    		@RequestParam(name="searchItem", defaultValue="boardTitle")String searchItem, 
			@RequestParam(name="searchWord", defaultValue="")String searchWord,
    		Model model) {
    	
    	// 1-1) songinfo 하드 코딩
    	SongDTO songDTO = new SongDTO();
        songDTO.setId((long) 1);  // 하드코딩된 ID 값
        songDTO.setTitle("하드코딩된 노래 제목");
        songDTO.setSinger("하드코딩된 가수");
        songDTO.setKyNumber("001");
        songDTO.setTjNumber("002");
        // songDTO 객체를 모델에 추가
        model.addAttribute("songDTO", songDTO);
        
        // 1-2) 실제 코드(오류 有)
//    	SongDTO song = service.getSongById(songDTO);
//    	model.addAttribute("songDTO", songDTO);
//   	
        
        // 2-1)댓글 검색 구현
        List<ReplyDTO> list = replyService.selectAll(searchItem, searchWord);
		
		model.addAttribute("list", list);
		model.addAttribute("searchItem", searchItem);	//검색 이후 클라이언트에 검색 결과를 남기기 위해
		model.addAttribute("searchWord", searchWord);	//검색 이후 클라이언트에 검색 결과를 남기기 위해
       return "song/songInfo";
   }
    
    // 2. 노래창 검색
    @GetMapping("/songSearch")
    public String searchSong(@RequestParam("songId") SongDTO songId, @RequestParam("query") String query, Model model
        ) {
            // 1. 노래 정보 조회
            SongDTO songDTO = songService.getSongById(songId);

            // 2. 검색어 기반 댓글 조회
            List<ReplyDTO> replies = replyService.searchReplies(query);

            // 3. 모델에 데이터 추가
            model.addAttribute("songDTO", songDTO);
            model.addAttribute("replies", replies);
            model.addAttribute("query", query); // 검색어 유지
            model.addAttribute("songId", songId); // songId 유지

            return "song/songInfo";
    }
    
    //3. 좋아요 구현
    @PostMapping("/like")
    public ResponseEntity<Map<String, Integer>> activeLike(@RequestBody Map<String, Long> requestData) {
        Long songId = requestData.get("songId");

        Integer newStatus = songService.activeLike(songId);
        log.info("상태값: {}", newStatus);
        
        // 새로운 상태를 반환
        Map<String, Integer> response = new HashMap<>();
        response.put("newStatus", newStatus);

        return ResponseEntity.ok(response);
    }

}