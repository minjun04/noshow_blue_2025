package noshow.Noshow_blue_2025.infra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "seat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {

    @Id
    @Column(name="seatId",nullable = false)
    private String seatId;

    @Column(nullable = false)
    private Boolean reserved;

    @Column(nullable = false)
    private Integer numOfExtensions;

    @Column(nullable = false)
    private long remainingBreakTime;

    private LocalDateTime startOfBreakTime;

    private LocalDateTime endOfBreakTime;

    private LocalDateTime startOfReservation;

    private LocalDateTime endOfReservation;

    @OneToOne
    @JoinColumn(name = "seatId", referencedColumnName = "seatId",
            foreignKey = @ForeignKey(name = "fk_seat_student", foreignKeyDefinition = "FOREIGN KEY (seatId) REFERENCES student(seatId) ON DELETE CASCADE"))
    private Student student;


}