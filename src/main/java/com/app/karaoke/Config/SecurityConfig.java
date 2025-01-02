package com.app.karaoke.Config;

import com.app.karaoke.DTO.UserDTO;
import com.app.karaoke.Entity.UserEntity;
import com.app.karaoke.Service.OAuth2UserService;
import com.app.karaoke.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;
    private final UserService userService;

    // 생성자 주입
    public SecurityConfig(OAuth2UserService oAuth2UserService, UserService userService) {
        this.oAuth2UserService = oAuth2UserService;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//       마지막에 csrf저거 없애고 각각의 api추가해줘야합니다
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()).csrf(csrf -> csrf.disable()) ;
        http.oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .successHandler(successHandler())
                .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
        );
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
            log.info(defaultOAuth2User.toString());
            Map<String, Object> attributes = defaultOAuth2User.getAttributes();
            log.info(attributes.toString());

            String kakaoId = null;
            String nickname = null;
            String email = null;

// 플랫폼별 정보 추출
            if (attributes.containsKey("id")) {  // 카카오 사용자
                kakaoId = attributes.get("id").toString();
                Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
                Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

                nickname = (properties != null) ? (String) properties.get("nickname") : null;
                email = (kakaoAccount != null) ? (String) kakaoAccount.get("email") : null;

            } else if (attributes.containsKey("sub")) {  // 구글 사용자
                kakaoId = attributes.get("sub").toString();
                nickname = (String) attributes.get("name");  // 전체 이름
                email = (String) attributes.get("email");
            }

            // (1) DB에서 중복 검사
            UserEntity existingUser = userService.findByKakaoId(kakaoId);

            if (existingUser != null) {
                // 이미 존재하는 회원 -> 세션에 정보 저장 후, 루트 페이지로 이동
                request.getSession().setAttribute("loginUserId", existingUser.getId());
                response.sendRedirect("/");
            } else {
                // 신규 회원 -> (2) 세션에 카카오 정보만 저장해두고 joindetail 페이지로 이동
                request.getSession().setAttribute("kakaoId", kakaoId);
                request.getSession().setAttribute("nickname", nickname);
                request.getSession().setAttribute("email", email);

                response.sendRedirect("/joindetail");
            }
        };
    }


}




