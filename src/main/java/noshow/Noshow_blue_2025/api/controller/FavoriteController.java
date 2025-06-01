package noshow.Noshow_blue_2025.api.controller;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.api.controller.dto.Favorite.addFavoriteRequest;
import noshow.Noshow_blue_2025.api.controller.dto.Favorite.deleteFavoriteRequest;
import noshow.Noshow_blue_2025.domain.service.FavoriteService;
import noshow.Noshow_blue_2025.infra.entity.Seat;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<List<Seat>> getFavoriteSeats(@AuthenticationPrincipal Student student) {
        List<Seat> favorites = favoriteService.getFavoriteSeatsByStudent(student);
        return ResponseEntity.ok(favorites);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFavorite(@AuthenticationPrincipal Student student,@RequestBody addFavoriteRequest request){
        boolean success = favoriteService.addFavorite(student, request.getSeatId());
        return ResponseEntity.ok(success);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFavorite(@AuthenticationPrincipal Student student,@RequestBody deleteFavoriteRequest request){
        boolean success = favoriteService.deleteFavorite(student, request.getSeatId());
        return ResponseEntity.ok(success);
    }
}
