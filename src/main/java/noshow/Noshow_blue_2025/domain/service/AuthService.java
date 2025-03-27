package noshow.Noshow_blue_2025.domain.service;

import noshow.Noshow_blue_2025.api.controller.dto.AuthLoginRequest;
import noshow.Noshow_blue_2025.api.controller.dto.AuthLoginResponse;
import noshow.Noshow_blue_2025.api.controller.dto.AuthSignUpRequest;
import noshow.Noshow_blue_2025.infra.entity.Student;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthService {
    private final StudentRepository studentRepository;

    //회원가입
    public AuthLoginResponse signUp(AuthSignUpRequest request) {
        if (studentRepository.findByEmail(request.getEmail()) != null) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
        System.out.println("회가성공");
        Student student = Student.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(request.getPassword())
                .studentId(request.getStudentId())
                .build();

        Student savedStudent = studentRepository.save(student);
        return AuthLoginResponse.from(savedStudent);
    }

    //로그인
    public AuthLoginResponse login(AuthLoginRequest request) {
        Student student = studentRepository.findByEmailAndPassword(
                request.getEmail(), request.getPassword());
        System.out.println("로인성공");
        if (student == null) {
            throw new IllegalArgumentException("일치하는 회원이 없습니다.");
        }

        return AuthLoginResponse.from(student);
    }
}
