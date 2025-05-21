package noshow.Noshow_blue_2025.api.controller;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.SeatSort.SeatRemainingNumOfExtensionResponse;
import noshow.Noshow_blue_2025.api.controller.dto.SeatSort.SeatRemainingTimeResponse;
import noshow.Noshow_blue_2025.domain.service.SeatSortService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seats")
public class SeatSortController {

    private final SeatSortService seatSortService;

    @GetMapping("/remaining-time")
    public ResponseEntity<List<SeatRemainingTimeResponse>> getLeastRemainingTimeSeats() {
        return ResponseEntity.ok(seatSortService.getTop5LeastRemainingTimeSeats());
    }

    @GetMapping("/remaining-NumOfExtension")
    public ResponseEntity<List<SeatRemainingNumOfExtensionResponse>> getLeastRemainingNumOfExtensionSeats() {
        return ResponseEntity.ok(seatSortService.getTop5LeastRemainingNumOfExtensionSeats());
    }

}
