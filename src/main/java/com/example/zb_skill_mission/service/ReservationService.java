package com.example.zb_skill_mission.service;

import com.example.zb_skill_mission.entity.ReservationEntity;
import com.example.zb_skill_mission.entity.ShopEntity;
import com.example.zb_skill_mission.entity.UserEntity;
import com.example.zb_skill_mission.model.Reservation;
import com.example.zb_skill_mission.model.constant.ReservationStatus;
import com.example.zb_skill_mission.repository.ReservationRepository;
import com.example.zb_skill_mission.repository.ShopRepository;
import com.example.zb_skill_mission.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    /**
     *  사용자가 매장 예약을 신청한다.
     */
    public Reservation.Id reserve(Reservation.Reserve reservation) {

        UserEntity user = userRepository.findById(reservation.getUserId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자"));

        ShopEntity shop = shopRepository.findById(reservation.getShopId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 매장"));

        if (reservation.getBookDatetime().isBefore(LocalDateTime.now().plusMinutes(10))) {
            throw new RuntimeException("10분 이후 시간으로 예약해야 합니다.");
        }
        if (reservationRepository.existsByBookDatetimeAndShopAndStatus(
                        reservation.getBookDatetime(), shop, ReservationStatus.ACCEPT)) {
            throw new RuntimeException("이미 존재하는 시간대");
        }

        ReservationEntity savedReservation =
                reservationRepository.save(ReservationEntity.toNewEntity(
                        user, shop, reservation.getBookDatetime()
                ));

        return new Reservation.Id(savedReservation.getId());
    }

    /**
     * 예약 시간 전, 매장에 방문하여 예약을 확정한다.
     */
    public void confirm(Long userId, Long reservationId) {
        ReservationEntity updateEntity =
                reservationRepository.findById(reservationId)
                        .orElseThrow(()->new RuntimeException("존재하지 않는 예약 번호"));

        if (!updateEntity.getUser().getId().equals(userId)) {
            throw new RuntimeException("예약 접근 권한이 없습니다.");
        }
        if (updateEntity.getStatus()!=ReservationStatus.ACCEPT) {
            throw new RuntimeException("거절된 예약입니다.");
        }

        if (updateEntity.getBookDatetime().isBefore(LocalDateTime.now().plusMinutes(10))) {
            throw new RuntimeException("방문 가능 시간이 지났습니다.");
        }
        updateEntity.setConfirmed(true);
        reservationRepository.save(updateEntity);
    }

    /**
     * 예약 시, 매장에서 승인 또는 거절한다.
     */
    public void accept(Reservation.Accept accept) {
        ReservationEntity entity =
                reservationRepository.findById(accept.getReservationId())
                        .orElseThrow(()->new RuntimeException("존재하지 않는 예약 번호"));

        if (!entity.getShop().getUser().getId().equals(accept.getUserId())) {
            throw new RuntimeException("예약 접근 권한이 없습니다.");
        }

        if (accept.isAccept()) { // accept가 true면 ACCEPT, 아니면 DENIED
            entity.setStatus(ReservationStatus.ACCEPT);
        } else {
            entity.setStatus(ReservationStatus.DENIED);
        }

        reservationRepository.save(entity);
    }
}
