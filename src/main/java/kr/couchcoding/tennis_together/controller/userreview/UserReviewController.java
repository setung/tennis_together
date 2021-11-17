package kr.couchcoding.tennis_together.controller.userreview;

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

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class UserReviewController {

    private final UserReviewService userReviewService;

    @PostMapping
    public void postUserReview(@RequestBody RequestUserReviewDTO userReviewDTO, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        userReviewService.postUserReview(user, userReviewDTO);
    }

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

    @GetMapping("/{reviewNo}")
    public ResponseUserReviewDTO findUserReview(@PathVariable Long reviewNo) {
        return new ResponseUserReviewDTO(userReviewService.findByReviewNo(reviewNo));
    }

    @DeleteMapping("/{reviewNo}")
    public void findUserReview(@PathVariable Long reviewNo, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        userReviewService.deleteReview(user, reviewNo);
    }
}
