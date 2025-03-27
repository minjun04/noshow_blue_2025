package noshow.Noshow_blue_2025.api.controller;

import noshow.Noshow_blue_2025.api.controller.dto.AuthLoginRequest;
import noshow.Noshow_blue_2025.api.controller.dto.AuthLoginResponse;
import noshow.Noshow_blue_2025.api.controller.dto.AuthSignUpRequest;
import noshow.Noshow_blue_2025.domain.service.AuthService;
import noshow.Noshow_blue_2025.domain.service.*;
import noshow.Noshow_blue_2025.api.controller.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/students")
    public AuthLoginResponse signUp(@RequestBody AuthSignUpRequest request) {
        return authService.signUp(request);
    }

    // 로그인
    @PostMapping("/students/login")
    public AuthLoginResponse login(@RequestBody AuthLoginRequest request) {
        return authService.login(request);
    }


}