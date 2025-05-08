package noshow.Noshow_blue_2025.api.controller;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.Gps.GpsRequest;
import noshow.Noshow_blue_2025.domain.service.GpsService;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gps")
@RequiredArgsConstructor
public class GpsController {

    private final GpsService gpsService;

    @PostMapping
    public ResponseEntity<String> receiveGps(@RequestBody GpsRequest gpsRequest,
                                             @AuthenticationPrincipal Student student) {
        String studentId = student.getStudentId();  // 또는 getId()
        String responseMessage = gpsService.processGpsRequest(gpsRequest, studentId);
        return ResponseEntity.ok(responseMessage);
    }
}
