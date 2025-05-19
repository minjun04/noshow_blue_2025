package noshow.Noshow_blue_2025.api.controller;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.Outing.OutingRequest;
import noshow.Noshow_blue_2025.domain.service.OutingService;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/outing")
@RequiredArgsConstructor
public class OutingController {

    private final OutingService outingService;

    @GetMapping("/check")
    public ResponseEntity<?> checkSeatAssignment(){
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean reserved = outingService.checkSeatAssignment(student);
        return ResponseEntity.ok(reserved);
    }

    @PostMapping("/break")
    public ResponseEntity<?> handleBreak(@RequestBody OutingRequest request) {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean result = outingService.handleBreakOrReturn(student, request.isBreak());
        return ResponseEntity.ok(result);
    }


}
