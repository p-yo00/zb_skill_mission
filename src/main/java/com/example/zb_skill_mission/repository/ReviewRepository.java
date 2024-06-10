package com.example.zb_skill_mission.repository;

import com.example.zb_skill_mission.entity.ReservationEntity;
import com.example.zb_skill_mission.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    boolean existsByReservation(ReservationEntity reservation);
}
