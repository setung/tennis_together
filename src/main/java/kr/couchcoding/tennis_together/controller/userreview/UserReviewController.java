package kr.couchcoding.tennis_together.controller.userreview;

import kr.couchcoding.tennis_together.controller.userreview.dto.RequestUserReviewDTO;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.domain.user_review.service.UserReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
