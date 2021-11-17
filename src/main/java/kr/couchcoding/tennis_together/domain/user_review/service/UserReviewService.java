package kr.couchcoding.tennis_together.domain.user_review.service;

import kr.couchcoding.tennis_together.controller.userreview.dto.RequestUserReviewDTO;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.model.GameUserList;
import kr.couchcoding.tennis_together.domain.game.service.GameUserListService;
import kr.couchcoding.tennis_together.domain.game.status.GameStatus;
import kr.couchcoding.tennis_together.domain.game.status.GameUserListStatus;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.domain.user_review.dao.UserReviewRepository;
import kr.couchcoding.tennis_together.domain.user_review.model.UserReview;
import kr.couchcoding.tennis_together.exception.CustomException;
import kr.couchcoding.tennis_together.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

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

        if (game.getEndDt().isAfter(LocalDate.now()) || game.getGameStatus() != GameStatus.CLOSED)
            throw new CustomException(ErrorCode.BAD_REQUEST_USER_REVIEW, "게임이 끝난 후 리뷰를 등록할 수 있습니다.");

        UserReview userReview = UserReview.builder()
                .writtenUser(writer)
                .recipient(recipient)
                .reviewContent(userReviewDTO.getReviewContent())
                .game(game)
                .score(userReviewDTO.getScore())
                .build();

        userReviewRepository.save(userReview);

        Double avg = userReviewRepository.getAverageScore(recipient);
        recipient.updateScore(avg.longValue());
    }

}
