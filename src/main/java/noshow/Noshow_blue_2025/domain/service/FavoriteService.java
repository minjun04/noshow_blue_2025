package noshow.Noshow_blue_2025.domain.service;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.domain.repositoryInterface.FavoriteRepository;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
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

}
