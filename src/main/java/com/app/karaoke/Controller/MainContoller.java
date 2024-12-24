package com.app.karaoke.Controller;

import com.app.karaoke.DTO.UserDTO;
import com.app.karaoke.Entity.PlayListEntity;
import com.app.karaoke.Entity.UserEntity;
import com.app.karaoke.Service.PlayListService;
import com.app.karaoke.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainContoller {

	@Autowired
	private final UserService userService;

	@Autowired
	private PlayListService playListService;

	public MainContoller(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public String mainpage() {
		return "mainPage";
	}

	@GetMapping("/login")
	public String loginpage() {
		return "login";
	}

	@GetMapping("/join")
	public String join() {
		return "join";
	}

	@GetMapping("/joindetail")
	public String joinDetailPage(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			// 세션에서 카카오 정보 꺼내기
			String kakaoId = (String) session.getAttribute("kakaoId");
			String nickname = (String) session.getAttribute("nickname");
			String email = (String) session.getAttribute("email");

			// 모델에 담아 뷰로 전달
			model.addAttribute("kakaoId", kakaoId);
			model.addAttribute("nickname", nickname);
			model.addAttribute("email", email);
		}
		return "joindetail"; // -> templates/joindetail.html
	}

	@PostMapping("/joindetail")
	public String joinDetailSubmit(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			// 세션이 없으면 에러 처리
			return "redirect:/error";
		}

		String kakaoId = (String) session.getAttribute("kakaoId");
		String nickname = (String) session.getAttribute("nickname");
		String email = (String) session.getAttribute("email");

		// DB 저장 (UserDTO 만들거나 직접)
		UserDTO userDTO = new UserDTO();
		userDTO.setKakaoNumber(kakaoId);
		userDTO.setUserName(nickname);
		userDTO.setUserEmail(email);

		// userService.add(...) 저장
		UserEntity savedUser = userService.add(userDTO);

		// 가입 완료 후, 세션에 로그인 정보 저장
		session.setAttribute("loginUserId", savedUser.getId());

		// 메인 페이지로 리다이렉트
		return "redirect:/";
	}
	
}
