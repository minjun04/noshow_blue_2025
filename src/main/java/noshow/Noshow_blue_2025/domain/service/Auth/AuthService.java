package noshow.Noshow_blue_2025.domain.service.Auth;

import noshow.Noshow_blue_2025.api.controller.dto.Auth.AuthLoginRequest;
import noshow.Noshow_blue_2025.api.controller.dto.Auth.AuthLoginResponse;
import noshow.Noshow_blue_2025.api.controller.dto.Auth.AuthSignUpRequest;
import noshow.Noshow_blue_2025.infra.entity.Student;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthService {
    private final StudentRepository studentRepository;
    private final JwtTokenProvider jwtTokenProvider;

    //로그인
    public ResponseEntity<?> login(AuthLoginRequest request) {
        Student student = studentRepository.findByEmail(request.getEmail());

        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("success", false, "reason", "USER_NOT_FOUND")
            );
        }
        // 실제 로그인 처리: 예) 토큰 생성, DTO 반환
        String token = jwtTokenProvider.generateToken(student.getEmail());
        return ResponseEntity.ok(new AuthLoginResponse(token));  // 200 OK with response body
    }

    //회원가입
    public ResponseEntity<?> signUp(AuthSignUpRequest request) {
        // 1. 중복 이메일 검사
        if (studentRepository.findByEmail(request.getEmail())!=null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("success", false, "reason", "EMAIL_ALREADY_EXISTS")
            );
        }

        // 2. 새로운 학생 객체 생성 및 저장
        Student student = Student.builder()
                .email(request.getEmail())
                .studentId(request.getStudentId())
                .name(request.getName())
                .build();

        studentRepository.save(student);

        // 3. 가입 성공 응답
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of("success", true, "message", "회원가입이 완료되었습니다.")
        );
    }
}
