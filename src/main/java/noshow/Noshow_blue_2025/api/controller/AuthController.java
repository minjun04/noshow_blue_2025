package noshow.Noshow_blue_2025.api.controller;

import noshow.Noshow_blue_2025.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.infra.entity.Student;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import org.springframework.web.bind.annotation.*;
import  org.springframework.ui.Model;
import  org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login-success")
    public void loginSuccess(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
        // Google OAuth2 로그인을 통해 가져온 사용자 정보
        String email = oauth2User.getAttribute("email");
        authService.login(email);
    }

    @PostMapping("/register")
    public String registerStudent(@RequestParam String studentId, @RequestParam String name, @RequestParam String email) {


        return "redirect:/home";  // 회원가입 후 홈 페이지로 리디렉션
    }
}