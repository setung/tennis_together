package kr.couchcoding.tennis_together.domain.userreview.service;

import kr.couchcoding.tennis_together.controller.userreview.dto.RequestUserReviewDTO;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.model.GameUserList;
import kr.couchcoding.tennis_together.domain.game.service.GameUserListService;
import kr.couchcoding.tennis_together.domain.game.status.GameStatus;
import kr.couchcoding.tennis_together.domain.game.status.GameUserListStatus;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.domain.userreview.dao.UserReviewRepository;
import kr.couchcoding.tennis_together.domain.userreview.model.UserReview;
import kr.couchcoding.tennis_together.exception.CustomException;
import kr.couchcoding.tennis_together.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserReviewService {

    private final UserReviewRepository userReviewRepository;
    private final GameUserListService gameUserListService;

    public void postUserReview(User writer, RequestUserReviewDTO userReviewDTO) {
        GameUserList gameUserList = gameUserListService.findByGameAndStatus(userReviewDTO.getGameNo(), GameUserListStatus.APPROVED);
        Game game = gameUserList.getJoinedGame();

        User recipient;
        User approvedUser = gameUserList.getGameUser();
        User gameCreator = game.getGameCreator();

        userReviewRepository.findByWrittenUserAndGame(writer, game)
                .ifPresent(review -> {
                    throw new CustomException(ErrorCode.BAD_REQUEST_USER_REVIEW, "작성하신 리뷰가 이미 존재합니다.");
                });

        if (gameCreator.getUid().equals(writer.getUid())) {
            recipient = approvedUser;
        } else if (approvedUser.getUid().equals(writer.getUid())) {
            recipient = gameCreator;
        } else
            throw new CustomException(ErrorCode.FORBIDDEN_USER, "리뷰를 작성할 권한이 없습니다.");

        if (game.getEndDt().isAfter(LocalDate.now(ZoneId.of("+09:00:00"))) || game.getGameStatus() != GameStatus.CLOSED)
            throw new CustomException(ErrorCode.BAD_REQUEST_USER_REVIEW, "게임이 끝난 후 리뷰를 등록할 수 있습니다.");

        UserReview userReview = UserReview.builder()
                .writtenUser(writer)
                .recipient(recipient)
                .reviewContent(userReviewDTO.getReviewContent())
                .game(game)
                .score(userReviewDTO.getScore())
                .build();

        userReviewRepository.save(userReview);
        updateUserScore(recipient);
    }

    public Page<UserReview> findAll(Specification<UserReview> spec, Pageable pageable) {
        return userReviewRepository.findAll(spec, pageable);
    }

    public UserReview findByReviewNo(Long reviewNo) {
        Optional<UserReview> userReview = userReviewRepository.findById(reviewNo);
        return userReview.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_REVIEW));
    }

    public void deleteReview(User user, Long reviewNo) {
        UserReview review = findByReviewNo(reviewNo);

        if (!review.getWrittenUser().getUsername().equals(user.getUsername()))
            throw new CustomException(ErrorCode.FORBIDDEN_USER);

        userReviewRepository.delete(review);
        updateUserScore(review.getRecipient());
    }

    private void updateUserScore(User user) {
        Double avg = userReviewRepository.getAverageScore(user);
        user.updateScore(avg == null ? 0 : avg.longValue());
    }

    public void updateReview(User user, Long reviewNo, RequestUserReviewDTO updatedReviewDTO) {
        UserReview review = findByReviewNo(reviewNo);

        if (!review.getWrittenUser().getUsername().equals(user.getUsername()))
            throw new CustomException(ErrorCode.FORBIDDEN_USER);

        review.updateReview(updatedReviewDTO);
        updateUserScore(review.getRecipient());
    }
}
