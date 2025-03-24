package noshow.Noshow_blue_2025.service;

import noshow.Noshow_blue_2025.controller.dto.*;
import noshow.Noshow_blue_2025.repository.Student;
import noshow.Noshow_blue_2025.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

        if (student == null) {
            throw new IllegalArgumentException("일치하는 회원이 없습니다.");
        }

        return AuthLoginResponse.from(student);
    }
}
