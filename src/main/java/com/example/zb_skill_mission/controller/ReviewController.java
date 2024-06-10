package com.example.zb_skill_mission.controller;

import com.example.zb_skill_mission.model.LoginUser;
import com.example.zb_skill_mission.model.Review;
import com.example.zb_skill_mission.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    /**
     * 사용자가 신규 작성한 리뷰를 저장한다.
     * body: reservationId, content, star
     */
    @PreAuthorize("hasRole('ROLE_GENERAL')")
    @PostMapping
    public ResponseEntity<Review.Id> writeReview(@LoginUser Long userId,
                                                 @RequestBody @Valid Review.Write review) {
        review.setUserId(userId);
        return ResponseEntity.ok(reviewService.writeReview(review));
    }

    /**
     * 작성했던 리뷰를 수정한다.
     * body: reviewId, content, star
     */
    @PreAuthorize("hasRole('ROLE_GENERAL')")
    @PutMapping
    public ResponseEntity<Review.Id> editReview(@LoginUser Long userId,
                                                @RequestBody @Valid Review.Edit review) {
        review.setUserId(userId);
        return ResponseEntity.ok(reviewService.editReview(review));
    }

    /**
     * 작성한 리뷰를 삭제한다.
     */
    @PreAuthorize("hasRole('ROLE_GENERAL')")
    @DeleteMapping("/{reviewId}")
    public void deleteReview(@LoginUser Long userId,
                             @PathVariable("reviewId") Long reviewId) {
        reviewService.deleteReview(userId, reviewId);
    }
}
