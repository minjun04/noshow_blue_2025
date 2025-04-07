package noshow.Noshow_blue_2025.domain.service;

import noshow.Noshow_blue_2025.api.controller.dto.AuthLoginResponse;
import noshow.Noshow_blue_2025.infra.entity.Student;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthService {
    private final StudentRepository studentRepository;

//    //회원가입
//    public AuthLoginResponse signUp(AuthSignUpRequest request) {
//        if (studentRepository.findByEmail(request.getEmail()) != null) {
//            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
//        }
//        System.out.println("회가성공");
//        Student student = Student.builder()
//                .email(request.getEmail())
//                .name(request.getName())
//                .password(request.getPassword())
//                .studentId(request.getStudentId())
//                .build();
//
//        Student savedStudent = studentRepository.save(student);
//        return AuthLoginResponse.from(savedStudent);
//    }

    //로그인
    public AuthLoginResponse login(String email) {
        // 이메일로 기존 사용자 확인
        Student existingStudent = studentRepository.findByEmail(email);

        // 이미 가입된 사용자라면, 로그인 처리 후 홈으로 리디렉션
        if (existingStudent!=null) {
            return AuthLoginResponse.from(existingStudent);  // 이미 가입된 경우
        }

        // 가입되지 않은 사용자라면, 학번과 이름을 입력받는 페이지로 이동
        throw new IllegalArgumentException("일치하는 회원이 없습니다.");
    }
}
