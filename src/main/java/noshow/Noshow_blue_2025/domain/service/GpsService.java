package noshow.Noshow_blue_2025.domain.service;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.Gps.GpsRequest;
import noshow.Noshow_blue_2025.domain.repositoryInterface.SeatRepository;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import noshow.Noshow_blue_2025.infra.entity.Seat;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GpsService {

    private final StudentRepository studentRepository;
    private final ReservationService reservationService;

    private static final double LIBRARY_LAT = 37.2839;
    private static final double LIBRARY_LON = 127.0450;
    private static final double RADIUS_METERS = 100.0;
    private final SeatRepository seatRepository;

    public String processGpsRequest(GpsRequest gpsRequest, String studentId) {
        double distance = haversine(
                gpsRequest.getLatitude(), gpsRequest.getLongitude(),
                LIBRARY_LAT, LIBRARY_LON
        );

        boolean isInLibrary = distance <= RADIUS_METERS;

        // TODO: 출입 상태 처리 로직 (DB 업데이트 등)
        if (isInLibrary) {
            // 예: 도서관에 있는 상태로 기록
            return "사용자가 도서관 반경 안에 있습니다.";
        } else {
            // 예: 도서관 밖 → 자리 반납 또는 퇴실 처리
            Student student = studentRepository.findByStudentId(studentId);
            Seat seat = seatRepository.findBySeatId(student.getSeatId());
            reservationService.ExitSeat(studentId);
            return "사용자가 도서관 반경 밖입니다.";
        }
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
