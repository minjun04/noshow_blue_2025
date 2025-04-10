package noshow.Noshow_blue_2025.domain.service;

import noshow.Noshow_blue_2025.api.controller.dto.Auth.AuthLoginRequest;
import noshow.Noshow_blue_2025.api.controller.dto.Auth.AuthLoginResponse;
import noshow.Noshow_blue_2025.infra.entity.Student;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthService {
    private final StudentRepository studentRepository;

    //로그인
    public AuthLoginResponse login(AuthLoginRequest request) {
        Student student = studentRepository.findByEmail(
                request.getEmail());
        System.out.println("로인성공");
        if (student == null) {
            throw new IllegalArgumentException("일치하는 회원이 없습니다.");
        }

        return AuthLoginResponse.from(student);
    }
}
