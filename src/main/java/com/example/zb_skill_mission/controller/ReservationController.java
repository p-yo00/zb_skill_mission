package com.example.zb_skill_mission.controller;

import com.example.zb_skill_mission.model.LoginUser;
import com.example.zb_skill_mission.model.Reservation;
import com.example.zb_skill_mission.service.ReservationService;
import com.example.zb_skill_mission.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final UserService userService;

    /**
     * 사용자가 매장에 예약한다.
     * body: userId, shopId, bookDatetime
     */
    @PreAuthorize("hasRole('ROLE_GENERAL')")
    @PostMapping
    public ResponseEntity<?> reserve(@LoginUser Long userId,
                                     @RequestBody Reservation.Reserve reservation) {
        reservation.setUserId(userId);
        return ResponseEntity.ok(reservationService.reserve(reservation));
    }

    /**
     * 10분 전 방문해서 예약을 완료한다. - 시간이 10분 이전인지 확인
     * reservation is_succeed가 true로 변경된다.
     * pathVariable: reservationId
     */
    @PreAuthorize("hasRole('ROLE_GENERAL')")
    @PutMapping("/confirm/{reservationId}")
    public void confirm(@LoginUser Long userId,
                        @PathVariable("reservationId") Long reservationId) {
        reservationService.confirm(userId, reservationId);
    }

    /**
     * 예약이 들어오면 매장에서 승인 또는 거절한다.
     * 승인하면 status가 ACCEPT, 거절하면 DENIED가 된다.
     * pathVariable: reservationId
     * body: boolean
     */
    @PreAuthorize("hasRole('ROLE_PARTNER')")
    @PutMapping("/accept/{reservationId}")
    public void accept(@LoginUser Long userId,
                       @PathVariable("reservationId") Long reservationId,
                       @RequestBody Reservation.Accept accept) {
        accept.setUserId(userId);
        accept.setReservationId(reservationId);

        reservationService.accept(accept);
    }
}
