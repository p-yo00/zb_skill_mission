package com.example.zb_skill_mission.service;

import com.example.zb_skill_mission.entity.ReservationEntity;
import com.example.zb_skill_mission.entity.ReviewEntity;
import com.example.zb_skill_mission.entity.ShopEntity;
import com.example.zb_skill_mission.exception.ReservationException;
import com.example.zb_skill_mission.exception.ReviewException;
import com.example.zb_skill_mission.model.Review;
import com.example.zb_skill_mission.model.constant.ErrorCode;
import com.example.zb_skill_mission.repository.ReservationRepository;
import com.example.zb_skill_mission.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final ShopService shopService;

    /**
     * 작성한 리뷰를 저장한다.
     * return: reservationId
     */
    public Review.Id writeReview(Review.Write review) {
        // reservation 검증
        ReservationEntity reservation =
                reservationRepository.findById(review.getReservationId())
                        .orElseThrow(() -> new ReservationException(ErrorCode.NOT_EXIST_RESERVATION));

        if (!reservation.getUser().getId().equals(review.getUserId())) {
            throw new ReservationException(ErrorCode.NOT_AUTHORIZED);
        }
        if (!reservation.isConfirmed()) {
            throw new ReservationException(ErrorCode.NOT_CONFIRMED_RESERVATION);
        }
        if (reviewRepository.existsByReservation(reservation)) {
            throw new ReviewException(ErrorCode.ALREADY_WRITE_REVIEW);
        }
        // 리뷰를 db에 추가
        ReviewEntity savedReview =
                reviewRepository.save(ReviewEntity.toEntity(review,
                        reservation));

        // 매장 entity의 평균 별점을 수정한다.
        ShopEntity shop = reservation.getShop();
        shopService.updateStarPoint(shop,
                shop.getStarPointSum() + review.getStar(),
                shop.getStarPointCnt() + 1);

        return new Review.Id(savedReview.getId());
    }

    /**
     * 사용자가 기존 작성한 리뷰를 수정한다.
     * return: reservationId
     */
    public Review.Id editReview(Review.Edit editReview) {
        ReviewEntity originalReview =
                reviewRepository.findById(editReview.getReviewId())
                        .orElseThrow(() -> new ReviewException(ErrorCode.NOT_EXIST_REVIEW));

        if (!originalReview.getReservation().getUser().getId().equals(editReview.getUserId())) {
            throw new ReviewException(ErrorCode.NOT_AUTHORIZED, "리뷰 접근 권한이 " +
                    "없습니다.");
        }

        // 매장의 평균 별점 수정
        ShopEntity reviewShop = originalReview.getReservation().getShop();
        shopService.updateStarPoint(reviewShop,
                reviewShop.getStarPointSum() - originalReview.getStarPoint() + editReview.getStar()
                , reviewShop.getStarPointCnt());

        // 리뷰 수정하여 저장
        originalReview.setContent(editReview.getContent());
        originalReview.setStarPoint(originalReview.getStarPoint());
        reviewRepository.save(originalReview);

        return new Review.Id(originalReview.getId());
    }

    /**
     * 리뷰의 is_deleted 값이 true로 변경된다.
     */
    public void deleteReview(Long userId, Long reviewId) {
        // 리뷰 조회하고 검증
        ReviewEntity review =
                reviewRepository.findById(reviewId)
                        .orElseThrow(() -> new RuntimeException("존재하지 않는 리뷰"));

        ReservationEntity reservation = review.getReservation();
        // 리뷰를 작성한 사용자 또는 해당 매장 주인이 아니면 삭제 불가
        if (!reservation.getUser().getId().equals(userId)
                || !reservation.getShop().getUser().getId().equals(userId)) {
            throw new ReviewException(ErrorCode.NOT_AUTHORIZED, "리뷰 접근 권한이 " +
                    "없습니다.");
        }

        // 매장 별점에서 제외
        ShopEntity shop = review.getReservation().getShop();
        shopService.updateStarPoint(shop,
                shop.getStarPointSum() - review.getStarPoint(),
                shop.getStarPointCnt() - 1);

        // delete 값 변경
        review.setDeleted(true);
        reviewRepository.save(review);
    }
}
