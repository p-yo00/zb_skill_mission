package com.example.zb_skill_mission.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class Review {
    @Getter
    @Setter
    public static class Write {
        private Long reservationId;
        private Long userId;
        private String content;
        @Min(1)
        @Max(5)
        private int star;
    }

    @Getter
    @Setter
    public static class Edit {
        private Long reviewId;
        private Long userId;
        private String content;
        @Min(1)
        @Max(5)
        private int star;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Id {
        private Long reviewId;
    }
}
