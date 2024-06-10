package com.example.zb_skill_mission.service;

import com.example.zb_skill_mission.entity.ShopEntity;
import com.example.zb_skill_mission.entity.UserEntity;
import com.example.zb_skill_mission.model.Shop;
import com.example.zb_skill_mission.model.constant.Order;
import com.example.zb_skill_mission.repository.ShopRepository;
import com.example.zb_skill_mission.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopService {
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    /**
     * 매장 정보를 추가한다.
     */
    public Shop.Id addShop(Shop.Add shop) {
        UserEntity owner = userRepository.findById(shop.getUserId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자"));

        ShopEntity savedShop = shopRepository.save(ShopEntity.ToEntity(shop,
                owner));

        return new Shop.Id(savedShop.getId());
    }

    /**
     * 매장 정보를 수정한다.
     */
    public Shop.Id modifyShop(Shop.Modify shop) {
        ShopEntity updatedShop = shopRepository.findById(shop.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 매장"));

        if (!updatedShop.getUser().getId().equals(shop.getId())) {
            throw new RuntimeException("권한이 없습니다.");
        }

        updatedShop.updateEntity(shop);
        shopRepository.save(updatedShop);

        return new Shop.Id(updatedShop.getId());
    }

    /**
     * 매장 정보를 삭제한다.
     */
    public Shop.Id deleteShop(Long userId, Long shopId) {
        ShopEntity deleteShop = shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 매장"));

        if (!deleteShop.getUser().getId().equals(userId)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        shopRepository.deleteById(shopId);

        return new Shop.Id(shopId);
    }

    /**
     * id로 특정 매장 정보를 조회한다.
     */
    public Shop.GetDetail getShop(Long shopId) {
        ShopEntity findShop = shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("없는 매장입니다"));

        return Shop.GetDetail.toShopDetail(findShop);
    }

    /**
     * 정렬 기준으로 모든 매장을 조회한다.
     */
    public List<Shop.GetSimple> getShops(Order order, Float lat, Float lnt,
                                         Pageable pageable) {
        List<Shop.GetSimple> response = new ArrayList<>();
        Page<ShopEntity> result;

        if (order == Order.DICTIONARY) {
            result = shopRepository.findAllByOrderByShopName(pageable);
        } else if (order == Order.STAR) {
            result = shopRepository.findAllByOrderByStarPointDesc(pageable);
        } else if (order == Order.DISTANCE) {
            result = shopRepository.findAllByOrderByDistance(lat, lnt,
                    pageable);
        } else {
            result = shopRepository.findAll(pageable);
        }

        for (ShopEntity shopEntity : result) {
            response.add(Shop.GetSimple.builder()
                    .id(shopEntity.getId())
                    .shopName(shopEntity.getShopName())
                    .starPoint(shopEntity.getStarPoint())
                    .build());
        }

        return response;
    }

    /**
     * 매장의 별점(합,개수,평균)을 파라미터 값으로 변경한다.
     */
    public void updateStarPoint(ShopEntity shopEntity, Long starPointSum,
                                Long starPointCnt) {
        shopEntity.setStarPointCnt(starPointCnt);
        shopEntity.setStarPointSum(starPointSum);
        shopEntity.setStarPoint(Math.round(
                ((float) starPointSum / starPointCnt) * 10) / 10f);

        shopRepository.save(shopEntity);
    }
}
