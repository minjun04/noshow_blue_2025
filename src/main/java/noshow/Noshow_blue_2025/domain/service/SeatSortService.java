package noshow.Noshow_blue_2025.domain.service;

import noshow.Noshow_blue_2025.api.controller.dto.SeatSort.SeatRemainingNumOfExtensionResponse;
import noshow.Noshow_blue_2025.api.controller.dto.SeatSort.SeatRemainingTimeResponse;
import noshow.Noshow_blue_2025.domain.repositoryInterface.SeatRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatSortService {

    private final SeatRepository seatRepository;

    public List<SeatRemainingTimeResponse> getTop5LeastRemainingTimeSeats() {
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        LocalDateTime now = LocalDateTime.now();
        List<SeatRemainingTimeResponse> result = seatRepository.findTop5ByRemainingTime(PageRequest.of(0, 5)).stream()
                .map(seat -> new SeatRemainingTimeResponse(
                        seat.getSeatId(),
                        Duration.between(now, seat.getEndOfReservation()).toMinutes()
                ))
                .collect(Collectors.toList());
        System.out.println("hello" + result);
        return result;
    }

    public List<SeatRemainingNumOfExtensionResponse> getTop5LeastRemainingNumOfExtensionSeats() {

        LocalDateTime now = LocalDateTime.now();
        return seatRepository.findTop5ByNumOfExtensions(PageRequest.of(0, 5)).stream()
                .map(seat -> new SeatRemainingNumOfExtensionResponse(
                        seat.getSeatId(),
                        seat.getNumOfExtensions(),
                        Duration.between(now, seat.getEndOfReservation()).toMinutes()
                ))
                .collect(Collectors.toList());
    }

}
