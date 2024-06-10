package com.example.zb_skill_mission.entity;

import com.example.zb_skill_mission.model.Review;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "review")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private ReservationEntity reservation;
    private String content;
    private int starPoint;
    @CreatedDate
    private LocalDateTime createdDt;
    @LastModifiedDate
    private LocalDateTime modifiedDt;
    private boolean isDeleted;

    public static ReviewEntity toEntity(Review.Write review,
                                        ReservationEntity reservation) {
        return ReviewEntity.builder()
                .reservation(reservation)
                .content(review.getContent())
                .starPoint(review.getStar())
                .isDeleted(false)
                .build();
    }
}
