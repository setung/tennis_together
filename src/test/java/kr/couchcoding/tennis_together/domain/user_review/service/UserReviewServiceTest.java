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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserReviewServiceTest {

    @Mock
    UserReviewRepository userReviewRepository;
    @Mock
    GameUserListService gameUserListService;
    @InjectMocks
    UserReviewService userReviewService;

    @Test
    public void save_UserReview() {
        User gameCreator = User.builder()
                .uid("creator")
                .build();

        User recipient = User.builder()
                .uid("recipient")
                .build();

        Game game = Game.builder()
                .gameNo(1L)
                .gameCreator(gameCreator)
                .endDt(LocalDate.now())
                .gameStatus(GameStatus.CLOSED)
                .build();

        GameUserList gameUserList = GameUserList.builder()
                .joinedGame(game)
                .gameUser(recipient)
                .build();

        RequestUserReviewDTO requestUserReviewDTO = new RequestUserReviewDTO();
        requestUserReviewDTO.setGameNo(game.getGameNo());
        requestUserReviewDTO.setScore(5L);


        Mockito.when(gameUserListService.findByGameAndStatus(game.getGameNo(), GameUserListStatus.APPROVED)).thenReturn(gameUserList);
        Mockito.when(userReviewRepository.findByWrittenUserAndGame(gameCreator, game)).thenReturn(Optional.empty());

        userReviewService.postUserReview(gameCreator, requestUserReviewDTO);
    }

    @Test
    @DisplayName("두번 리뷰 작성시 예외 발생")
    public void save_UserReview_twice() {
        User gameCreator = User.builder()
                .uid("creator")
                .build();

        User recipient = User.builder()
                .uid("recipient")
                .build();

        Game game = Game.builder()
                .gameNo(1L)
                .gameCreator(gameCreator)
                .endDt(LocalDate.now())
                .gameStatus(GameStatus.CLOSED)
                .build();

        GameUserList gameUserList = GameUserList.builder()
                .joinedGame(game)
                .gameUser(recipient)
                .build();

        RequestUserReviewDTO requestUserReviewDTO = new RequestUserReviewDTO();
        requestUserReviewDTO.setGameNo(game.getGameNo());
        requestUserReviewDTO.setScore(5L);

        UserReview userReview = UserReview.builder()
                .build();

        Mockito.when(gameUserListService.findByGameAndStatus(game.getGameNo(), GameUserListStatus.APPROVED)).thenReturn(gameUserList);
        Mockito.when(userReviewRepository.findByWrittenUserAndGame(gameCreator, game)).thenReturn(Optional.of(userReview));

        Assertions.assertThrows(CustomException.class, () -> userReviewService.postUserReview(gameCreator, requestUserReviewDTO));
    }

    @Test
    @DisplayName("게임한 이력이 없는 유저에 리뷰 작성시 예외 발생")
    public void save_UserReview_wrong_user() {
        User gameCreator = User.builder()
                .uid("creator")
                .build();

        Game game = Game.builder()
                .gameNo(1L)
                .gameCreator(gameCreator)
                .endDt(LocalDate.now())
                .gameStatus(GameStatus.CLOSED)
                .build();

        RequestUserReviewDTO requestUserReviewDTO = new RequestUserReviewDTO();
        requestUserReviewDTO.setGameNo(game.getGameNo());
        requestUserReviewDTO.setScore(5L);

        Mockito.when(gameUserListService.findByGameAndStatus(game.getGameNo(), GameUserListStatus.APPROVED)).thenThrow(CustomException.class);

        Assertions.assertThrows(CustomException.class, () -> userReviewService.postUserReview(gameCreator, requestUserReviewDTO));
    }

    @Test
    @DisplayName("게임이 끝나기 전에 리뷰 작성시 예외 발생")
    public void save_UserReview_early() {
        User gameCreator = User.builder()
                .uid("creator")
                .build();

        Game game = Game.builder()
                .gameNo(1L)
                .gameCreator(gameCreator)
                .endDt(LocalDate.now().plusDays(1))
                .gameStatus(GameStatus.CLOSED)
                .build();

        RequestUserReviewDTO requestUserReviewDTO = new RequestUserReviewDTO();
        requestUserReviewDTO.setGameNo(game.getGameNo());
        requestUserReviewDTO.setScore(5L);

        GameUserList gameUserList = GameUserList.builder()
                .joinedGame(game)
                .build();

        Mockito.when(gameUserListService.findByGameAndStatus(game.getGameNo(), GameUserListStatus.APPROVED)).thenReturn(gameUserList);

        Assertions.assertThrows(CustomException.class, () -> userReviewService.postUserReview(gameCreator, requestUserReviewDTO));
    }
}