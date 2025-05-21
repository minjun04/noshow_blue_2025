package noshow.Noshow_blue_2025.domain.service;

import noshow.Noshow_blue_2025.api.controller.dto.SeatRemainingNumOfExtensionResponse;
import noshow.Noshow_blue_2025.api.controller.dto.SeatRemainingTimeResponse;
import noshow.Noshow_blue_2025.domain.repositoryInterface.SeatRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatSortService {

    private SeatRepository seatRepository;

    public List<SeatRemainingTimeResponse> getTop5LeastRemainingTimeSeats() {

        LocalDateTime now = LocalDateTime.now();

        return seatRepository.findAllReservedSeats().stream()
                .map(seat -> new SeatRemainingTimeResponse(
                        seat.getSeatId(),
                        Duration.between(now, seat.getEndOfReservation()).toMinutes()
                ))
                .sorted(Comparator.comparingLong(SeatRemainingTimeResponse::getRemainingMinutes))
                .limit(5)
                .collect(Collectors.toList());
    }

    public List<SeatRemainingNumOfExtensionResponse> getTop5LeastRemainingNumOfExtensionSeats() {

        LocalDateTime now = LocalDateTime.now();
        return seatRepository.findAllReservedSeats().stream()
                .map(seat -> new SeatRemainingNumOfExtensionResponse(
                        seat.getSeatId(),
                        seat.getNumOfExtensions(),
                        Duration.between(now, seat.getEndOfReservation()).toMinutes()
                ))
                .sorted(Comparator.comparingInt(SeatRemainingNumOfExtensionResponse::getNumOfExtensions)
                        .reversed()
                        .thenComparingLong(SeatRemainingNumOfExtensionResponse::getRemainingMinutes))
                .limit(5)
                .collect(Collectors.toList());
    }
}
