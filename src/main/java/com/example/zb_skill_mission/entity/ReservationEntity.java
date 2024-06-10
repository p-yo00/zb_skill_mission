package com.example.zb_skill_mission.entity;

import com.example.zb_skill_mission.model.constant.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservation")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private ShopEntity shop;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private LocalDateTime bookDatetime;
    private boolean isConfirmed;
    @Enumerated(value = EnumType.STRING)
    private ReservationStatus status;

    public static ReservationEntity toNewEntity(UserEntity userEntity,
                                                ShopEntity shopEntity,
                                                LocalDateTime bookDatetime) {
        return ReservationEntity.builder()
                .user(userEntity)
                .shop(shopEntity)
                .bookDatetime(bookDatetime)
                .isConfirmed(false)
                .status(ReservationStatus.CHECKING)
                .build();
    }
}
