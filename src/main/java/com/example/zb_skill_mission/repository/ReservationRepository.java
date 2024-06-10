package com.example.zb_skill_mission.repository;

import com.example.zb_skill_mission.entity.ReservationEntity;
import com.example.zb_skill_mission.entity.ShopEntity;
import com.example.zb_skill_mission.model.constant.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    boolean existsByBookDatetimeAndShopAndStatus(LocalDateTime datetime,
                                                        ShopEntity shop,
                                                        ReservationStatus status);
}
