package noshow.Noshow_blue_2025.domain.service;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.domain.repositoryInterface.FavoriteRepository;
import noshow.Noshow_blue_2025.domain.repositoryInterface.SeatRepository;
import noshow.Noshow_blue_2025.infra.entity.Favorite;
import noshow.Noshow_blue_2025.infra.entity.Seat;
import noshow.Noshow_blue_2025.infra.entity.Student;
import noshow.Noshow_blue_2025.security.FirebaseProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final SeatRepository seatRepository;
    private final FirebaseProperties firebaseProperties;

    RestTemplate restTemplate = new RestTemplate();

    public List<Seat> getFavoriteSeatsByStudent(Student student) {
        List<Favorite> favorites = favoriteRepository.findByStudent(student);
        return favorites.stream()
                .map(Favorite::getSeat)
                .collect(Collectors.toList());
    }

    public void sendFcmToStudent(Student student, String seatId) throws IOException {

        String fcmToken = student.getFcmToken();

        Map<String, Object> notification = Map.of(
                "title", "좌석 알림",
                "body", "선호 좌석 " + seatId + "이 비었습니다!"
        );

        Map<String, Object> message = Map.of(
                "token", fcmToken,
                "notification", notification
        );

        Map<String, Object> body = Map.of("message", message);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        restTemplate.postForEntity("https://fcm.googleapis.com/v1/projects/noshow2025-c3b7a/messages:send", request, String.class);


    }


    public boolean addFavorite(Student student, String seatId) {
        // 1. 좌석 찾기
        Seat seat = seatRepository.findBySeatId(seatId);
        if (seat == null) {
            throw new IllegalArgumentException("해당 좌석이 존재하지 않습니다: " + seatId);
        }

        // 2. 중복 즐겨찾기 체크
        boolean exists = favoriteRepository.existsByStudentAndSeat(student, seat);
        if (exists) {
            return false; // 이미 존재함
        }

        // 3. 즐겨찾기 생성 및 저장
        Favorite favorite = Favorite.builder()
                .student(student)
                .seat(seat)
                .build();
        favoriteRepository.save(favorite);

        return true;
    }

    public boolean deleteFavorite(Student student, String seatId) {
        // 1. 좌석 조회
        Seat seat = seatRepository.findBySeatId(seatId);
        if (seat == null) {
            throw new IllegalArgumentException("해당 좌석이 존재하지 않습니다: " + seatId);
        }

        // 2. 즐겨찾기 존재 여부 확인 및 조회
        Optional<Favorite> favoriteOptional = favoriteRepository.findByStudentAndSeat(student, seat);
        if (favoriteOptional.isEmpty()) {
            return false; // 즐겨찾기 없음
        }

        // 3. 즐겨찾기 삭제
        favoriteRepository.delete(favoriteOptional.get());
        return true;
    }

    public void notifyStudentsOfAvailableSeat(String seatId) throws IOException {
        List<Favorite> favorites = favoriteRepository.findBySeat_SeatId(seatId);

        for (Favorite favorite : favorites) {
            Student student = favorite.getStudent();

            if (student.getFcmToken() != null && !student.getFcmToken().isEmpty()) {
                sendFcmToStudent(student, seatId);
            }
        }
    }
    public String getAccessToken() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        String path = firebaseProperties.getServiceAccountPath().replace("classpath:", "");
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
       // InputStream inputStream = classLoader.getResourceAsStream(firebaseProperties.getServiceAccountPath());
        // 기존 로직 유지
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(inputStream)
                .createScoped(List.of("https://www.googleapis.com/auth/firebase.messaging"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

}
