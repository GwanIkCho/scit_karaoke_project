package com.app.karaoke.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/recommendations")
@Slf4j
public class PythonAPI {

    private final RestTemplate restTemplate;

    @Value("${flask.api.url}")
    private String flaskApiUrl;

    // RestTemplate 의존성 주입
    public PythonAPI(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<?> getRecommendations(HttpServletRequest request) {
        // 세션에서 userId 추출
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUserId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유저 정보 없음");
        }
        Long userId = (Long) session.getAttribute("loginUserId");

        // Flask로 보낼 데이터 구성
        Map<String, Object> requestBody = Map.of("userId", userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Flask 추천 API 호출
            ResponseEntity<String> response = restTemplate.exchange(
                    flaskApiUrl + "/recommendations",
                    HttpMethod.POST,
                    entity,
                    String.class
            );
            log.info("Flask 추천 결과: {}", response.getBody());
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            log.error("Flask API 호출 중 에러 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("추천 불러오기 실패: " + e.getMessage());
        }
    }
}
