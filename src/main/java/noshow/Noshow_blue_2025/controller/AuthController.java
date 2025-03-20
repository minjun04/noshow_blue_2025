package noshow.Noshow_blue_2025.controller;
import noshow.Noshow_blue_2025.service.*;
import noshow.Noshow_blue_2025.controller.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/auth/students")
    public AuthLoginResponse signUp(@RequestBody AuthSignUpRequest request) {
        return authService.signUp(request);
    }

    // 로그인
    @PostMapping("/auth/students/login")
    public AuthLoginResponse login(@RequestBody AuthLoginRequest request) {
        return authService.login(request);
    }


}