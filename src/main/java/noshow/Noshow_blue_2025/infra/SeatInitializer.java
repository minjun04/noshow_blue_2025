package noshow.Noshow_blue_2025.infra;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.domain.repositoryInterface.SeatRepository;
import noshow.Noshow_blue_2025.infra.entity.Seat;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class SeatInitializer implements ApplicationRunner {

    private final SeatRepository seatRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (seatRepository.count() == 0) { // 이미 있으면 중복 방지
            IntStream.rangeClosed(1, 180).forEach(i -> {
                Seat seat = Seat.builder()
                        .seatId(String.valueOf(i))
                        .reserved(false)
                        .numOfExtensions(0)
                        .remainingBreakTime(210) // 3시간 30분
                        .build();
                seatRepository.save(seat);
            });
            System.out.println("Seat 테이블에 1~180번 좌석 초기화 완료");
        } else {
            System.out.println("Seat 테이블은 이미 초기화되어 있음");
        }
    }
}
