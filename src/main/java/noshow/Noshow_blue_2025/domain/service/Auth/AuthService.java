package noshow.Noshow_blue_2025.domain.service.Auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import noshow.Noshow_blue_2025.api.controller.dto.Auth.AuthLoginRequest;
import noshow.Noshow_blue_2025.api.controller.dto.Auth.AuthLoginResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import noshow.Noshow_blue_2025.api.controller.dto.Auth.AuthSignUpRequest;
import noshow.Noshow_blue_2025.infra.entity.Student;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthService {
    private final StudentRepository studentRepository;
    private final JwtTokenProvider jwtTokenProvider;

    //로그인
    public ResponseEntity<?> login(AuthLoginRequest request) {
        String idTokenString = request.getIdToken();

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance()
            )
                    .setAudience(Collections.singletonList("354675947931-651m2sulv6s8kik4injhdod2bq2h0vt0.apps.googleusercontent.com")) //
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        Map.of("success", false, "reason", "INVALID_ID_TOKEN")
                );
            }

            String email = idToken.getPayload().getEmail(); // 구글 계정 이메일 추출
            Student student = studentRepository.findByEmail(email);

            if (student == null) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        Map.of("success", false, "reason", "USER_NOT_FOUND")
                );
            }

            String token = jwtTokenProvider.generateToken(student.getEmail());
            student.setFcmToken(request.getFcmToken());
            studentRepository.save(student);
            return ResponseEntity.ok(new AuthLoginResponse(token));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("success", false, "reason", "SERVER_ERROR")
            );
        }
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
