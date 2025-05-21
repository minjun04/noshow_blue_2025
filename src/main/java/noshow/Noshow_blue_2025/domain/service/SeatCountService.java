package noshow.Noshow_blue_2025.domain.service;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.domain.repositoryInterface.SeatRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import noshow.Noshow_blue_2025.api.controller.dto.SeatStatus.SeatCountResponse;

@Service
@RequiredArgsConstructor
public class SeatCountService {
    private final SeatRepository seatRepository;

    public int getSeatCount(){
        return seatRepository.findSeatCount();
    }
}
