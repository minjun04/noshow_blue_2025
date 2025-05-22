package noshow.Noshow_blue_2025.domain.service;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.domain.repositoryInterface.FavoriteRepository;
import noshow.Noshow_blue_2025.domain.repositoryInterface.SeatRepository;
import noshow.Noshow_blue_2025.infra.entity.Favorite;
import noshow.Noshow_blue_2025.infra.entity.Seat;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final SeatRepository seatRepository;
    RestTemplate restTemplate = new RestTemplate();

    public List<Seat> getFavoriteSeatsByStudent(Student student) {
        List<Favorite> favorites = favoriteRepository.findByStudent(student);
        return favorites.stream()
                .map(Favorite::getSeat)
                .collect(Collectors.toList());
    }

    public void sendFcmToStudent(Student student, String seatId) {
        String fcmToken = student.getFcmToken();

        Map<String, Object> body = Map.of(
                "to", fcmToken,
                "notification", Map.of(
                        "title", "좌석 알림",
                        "body", "선호 좌석 " + seatId + "이 비었습니다!"
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "key=BLKyPCURTsLPtaruv57WNAV2lYeXj5EuJgl-L5SLNZ4i5-rj5EKmlW9TMXaV9M0NUt6zYzWI44vbIc9SPoJFhJ4"); // Firebase > 프로젝트 설정 > 클라우드 메시징
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        restTemplate.postForEntity("https://fcm.googleapis.com/fcm/send", request, String.class);
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

    public void notifyStudentsOfAvailableSeat(String seatId) {
        List<Favorite> favorites = favoriteRepository.findBySeat_SeatId(seatId);

        for (Favorite favorite : favorites) {
            Student student = favorite.getStudent();

            if (student.getFcmToken() != null && !student.getFcmToken().isEmpty()) {
                sendFcmToStudent(student, seatId);
            }
        }
    }
}
