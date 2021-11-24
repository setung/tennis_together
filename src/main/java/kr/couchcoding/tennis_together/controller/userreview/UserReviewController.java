package kr.couchcoding.tennis_together.controller.userreview;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.couchcoding.tennis_together.controller.userreview.dto.RequestUserReviewDTO;
import kr.couchcoding.tennis_together.controller.userreview.dto.ResponseUserReviewDTO;
import kr.couchcoding.tennis_together.controller.userreview.specification.UserReviewSpecification;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.domain.userreview.model.UserReview;
import kr.couchcoding.tennis_together.domain.userreview.service.UserReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"User 평점관련 API"})
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class UserReviewController {

    private final UserReviewService userReviewService;

    @ApiOperation(value = "User 평점 등록 API")
    @PostMapping
    public void postUserReview(@RequestBody RequestUserReviewDTO userReviewDTO, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        userReviewService.postUserReview(user, userReviewDTO);
    }

    @ApiOperation(value = "User 평점 전체조회 API")
    @GetMapping
    public Page<ResponseUserReviewDTO> findAllUserReviews(
            @RequestParam(required = false) String writtenUserUid,
            @RequestParam(required = false) String recipientUid,
            @RequestParam(required = false) Long gameNo,
            @RequestParam(required = false) Long score,
            Pageable pageable) {

        Specification<UserReview> spec = (root, query, criteriaBuilder) -> null;
        if (writtenUserUid != null) spec = spec.and(UserReviewSpecification.equalWriteUser(writtenUserUid));
        if (recipientUid != null) spec = spec.and(UserReviewSpecification.equalRecipient(recipientUid));
        if (gameNo != null) spec = spec.and(UserReviewSpecification.equalGameNo(gameNo));
        if (score != null) spec = spec.and(UserReviewSpecification.equalScore(score));

        return userReviewService.findAll(spec, pageable).map(userReview -> new ResponseUserReviewDTO(userReview));
    }

    @ApiOperation(value = "User 평점 상세조회 API")
    @GetMapping("/{reviewNo}")
    public ResponseUserReviewDTO findUserReview(@PathVariable Long reviewNo) {
        return new ResponseUserReviewDTO(userReviewService.findByReviewNo(reviewNo));
    }

    @ApiOperation(value = "User 평점 삭제 API")
    @DeleteMapping("/{reviewNo}")
    public void findUserReview(@PathVariable Long reviewNo, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        userReviewService.deleteReview(user, reviewNo);
    }

    @ApiOperation(value = "User 평점 수정 API")
    @PatchMapping("/{reviewNo}")
    public void updateUserReview(@PathVariable Long reviewNo,
                                 @RequestBody RequestUserReviewDTO updatedReviewDTO,
                                 Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        userReviewService.updateReview(user, reviewNo, updatedReviewDTO);
    }
}
