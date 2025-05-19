package noshow.Noshow_blue_2025.domain.service;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.SeatStatus.OtherSeatStatusResponse;
import noshow.Noshow_blue_2025.api.controller.dto.SeatStatus.SeatStatusResponse;
import noshow.Noshow_blue_2025.infra.entity.Seat;
import noshow.Noshow_blue_2025.domain.repositoryInterface.SeatRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtherUserSeatStatusService {

    private final SeatRepository seatRepository;

    public OtherSeatStatusResponse getOtherSeatStatus(String seatId) {
        // 좌석 정보 조회
        Seat seat = seatRepository.findBySeatId(seatId);

        long remainingMinutes = getRemainingMinutes(seatId);

        return new OtherSeatStatusResponse(
                seat.getSeatId(),
                remainingMinutes,
                seat.getNumOfExtensions()
        );
    }

    public long getRemainingMinutes(String seatId) {
        Seat seat = seatRepository.findBySeatId(seatId);
        if (seat.getEndOfReservation() == null) return 0;
        return Duration.between(LocalDateTime.now(), seat.getEndOfReservation()).toMinutes();
    }
}
