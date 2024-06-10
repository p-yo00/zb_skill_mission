package com.example.zb_skill_mission.controller;

import com.example.zb_skill_mission.model.LoginUser;
import com.example.zb_skill_mission.model.Shop;
import com.example.zb_skill_mission.model.constant.Order;
import com.example.zb_skill_mission.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    /**
     * 원하는 매장을 파라미터로 정렬 조건(거리순, 별점순, 사전순)을 주어 조회한다.
     * RequestParam: order(정렬 기준), lat(위도), lnt(경도), pageable
     */
    @GetMapping
    public ResponseEntity<List<?>> getShops(@RequestParam(value = "order",
                                            required = false,
                                            defaultValue = "DICTIONARY") Order order,
                                             @RequestParam(value = "lat",
                                                  required = false) Float lat,
                                             @RequestParam(value = "lnt",
                                                  required = false) Float lnt,
                                             Pageable pageable) {

        return ResponseEntity.ok(shopService.getShops(order, lat, lnt, pageable));
    }

    /**
     * 특정 매장의 상세 정보를 조회한다.
     */
    @GetMapping("/{shopId}")
    public ResponseEntity<Shop.GetDetail> getShop(@PathVariable Long shopId) {
        return ResponseEntity.ok(shopService.getShop(shopId));
    }

    /**
     * PARTNER 사용자가 매장을 추가한다.
     * body: Shop.Add - 매장 정보
     */
    @PreAuthorize("hasRole('ROLE_PARTNER')")
    @PostMapping
    public ResponseEntity<Shop.Id> addShop(@LoginUser Long userId,
                                           @RequestBody Shop.Add shop) {
        shop.setUserId(userId);
        return ResponseEntity.ok(shopService.addShop(shop));
    }

    /**
     * 매장의 정보를 수정한다.
     * body: Shop.Modify - 매장 정보
     */
    @PreAuthorize("hasRole('ROLE_PARTNER')")
    @PutMapping
    public ResponseEntity<Shop.Id> modifyShop(@LoginUser Long userId,
                                              @RequestBody Shop.Modify shop) {
        shop.setUserId(userId);
        return ResponseEntity.ok(shopService.modifyShop(shop));
    }

    /**
     * 등록한 매장을 삭제한다.
     */
    @PreAuthorize("hasRole('ROLE_PARTNER')")
    @DeleteMapping("/{shopId}")
    public ResponseEntity<Shop.Id> deleteShop(@LoginUser Long userId,
                                              @PathVariable Long shopId) {
        return ResponseEntity.ok(shopService.deleteShop(userId, shopId));
    }
}
