package noshow.Noshow_blue_2025.domain.service;

import noshow.Noshow_blue_2025.api.controller.dto.*;
import noshow.Noshow_blue_2025.infra.entity.Student;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthService {
    private final StudentRepository studentRepository;


    //로그인
    public AuthLoginResponse login(AuthLoginRequest request) {
        Student student = studentRepository.findByEmail(
                request.getEmail());
        System.out.println("로인성공");
        if (student == null) {
            System.out.println("일치 없음");
            throw new IllegalArgumentException("일치하는 회원이 없습니다.");
        }

        return AuthLoginResponse.from(student);
    }
}
