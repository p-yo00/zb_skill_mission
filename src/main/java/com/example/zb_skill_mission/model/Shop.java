package com.example.zb_skill_mission.model;

import com.example.zb_skill_mission.entity.ShopEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

public class Shop {
    @Getter
    @Setter
    public static class Add {
        private Long userId;
        private String shopName;
        private String shopPhoneNumber;
        private String address;
        private double lat;
        private double lnt;
        private LocalTime openTime;
        private LocalTime closeTime;
        private String description;
    }

    @Getter
    @Setter
    public static class Modify {
        private Long id;
        private Long userId;
        private String shopName;
        private String shopPhoneNumber;
        private String address;
        private double lat;
        private double lnt;
        private LocalTime openTime;
        private LocalTime closeTime;
        private String description;
    }

    @AllArgsConstructor
    @Getter
    public static class Id {
        private Long id;
    }

    @Builder
    @Getter
    public static class GetSimple {
        private Long id;
        private String shopName;
        private double starPoint;
    }

    @Builder
    @Getter
    public static class GetDetail {
        private String shopName;
        private String shopPhoneNumber;
        private String address;
        private double lat;
        private double lnt;
        private LocalTime openTime;
        private LocalTime closeTime;
        private String description;
        private double starPoint;

        public static GetDetail toShopDetail(ShopEntity shopEntity) {
            return GetDetail.builder()
                    .shopName(shopEntity.getShopName())
                    .shopPhoneNumber(shopEntity.getShopPhoneNumber())
                    .address(shopEntity.getAddress())
                    .lat(shopEntity.getLat())
                    .lnt(shopEntity.getLnt())
                    .openTime(shopEntity.getOpenTime())
                    .closeTime(shopEntity.getCloseTime())
                    .description(shopEntity.getDescription())
                    .starPoint(shopEntity.getStarPoint())
                    .build();
        }
    }
}
