package com.example.zb_skill_mission.entity;

import com.example.zb_skill_mission.model.Shop;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "shop")
public class ShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private String shopName;
    private String shopPhoneNumber;
    private String address;
    private double lat;
    private double lnt;
    @JsonFormat(pattern = "hh:mm")
    private LocalTime openTime;
    @JsonFormat(pattern = "hh:mm")
    private LocalTime closeTime;
    private String description;
    private long starPointSum;
    private long starPointCnt;
    private float starPoint;
    private int version;

    public static ShopEntity ToEntity(Shop.Add shop, UserEntity owner) {
        return ShopEntity.builder()
                .user(owner)
                .shopName(shop.getShopName())
                .shopPhoneNumber(shop.getShopPhoneNumber())
                .address(shop.getAddress())
                .lat(shop.getLat())
                .lnt(shop.getLnt())
                .openTime(shop.getOpenTime())
                .closeTime(shop.getCloseTime())
                .description(shop.getDescription())
                .starPoint(0)
                .starPointCnt(0)
                .starPointSum(0)
                .build();
    }

    public void updateEntity(Shop.Modify shop) {
        this.setShopName(shop.getShopName());
        this.setShopPhoneNumber(shop.getShopPhoneNumber());
        this.setAddress(shop.getAddress());
        this.setLat(shop.getLat());
        this.setLnt(shop.getLnt());
        this.setOpenTime(shop.getOpenTime());
        this.setCloseTime(shop.getCloseTime());
        this.setDescription(shop.getDescription());
    }
}
