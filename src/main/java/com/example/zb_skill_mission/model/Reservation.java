package com.example.zb_skill_mission.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class Reservation {
    @Getter
    @Setter
    public static class Reserve {
        private Long shopId;
        private Long userId;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime bookDatetime;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Id {
        private Long reservationId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Accept {
        private boolean accept;
        private Long reservationId;
        private Long userId;
    }
}
