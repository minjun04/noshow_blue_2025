package noshow.Noshow_blue_2025.infra.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorite")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="favoriteId")
    private Long favoriteId; // 문자열보다는 Long ID가 일반적

    @ManyToOne
    @JoinColumn(name = "studentId", nullable = false,
            foreignKey = @ForeignKey(name = "fk_favorite_student"))
    private Student student;

    @ManyToOne
    @JoinColumn(name = "seatId", nullable = false,
            foreignKey = @ForeignKey(name = "fk_favorite_seat"))
    private Seat seat;

}
