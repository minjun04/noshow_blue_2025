package noshow.Noshow_blue_2025.api.controller;

import noshow.Noshow_blue_2025.api.controller.dto.Auth.AuthLoginRequest;
import noshow.Noshow_blue_2025.api.controller.dto.Auth.AuthSignUpRequest;
import noshow.Noshow_blue_2025.domain.service.Auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody AuthSignUpRequest request) {
        return authService.signUp(request);
    }
}