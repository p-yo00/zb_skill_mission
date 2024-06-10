package com.example.zb_skill_mission.repository;

import com.example.zb_skill_mission.entity.ShopEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
    Optional<ShopEntity> findById(Long id);

    Page<ShopEntity> findAllByOrderByShopName(Pageable pageable);

    Page<ShopEntity> findAllByOrderByStarPointDesc(Pageable pageable);

    @Query(value = "SELECT * FROM shop ORDER BY (6371*acos(cos(radians(:lat))" +
            "*cos(radians(lat))" +
            "*cos(radians(lnt)-radians(:lnt))" +
            "+sin(radians(:lat))*sin(radians(lat))))", nativeQuery = true)
    Page<ShopEntity> findAllByOrderByDistance(@Param("lat") float lat,
                                              @Param("lnt") float lnt,
                                              Pageable pageable);
}
