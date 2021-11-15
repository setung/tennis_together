package kr.couchcoding.tennis_together.domain.game.service;

import kr.couchcoding.tennis_together.domain.game.dao.GameUserListRepository;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.model.GameUserList;
import kr.couchcoding.tennis_together.domain.game.status.GameStatus;
import kr.couchcoding.tennis_together.domain.game.status.GameUserListStatus;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.domain.user.service.UserService;
import kr.couchcoding.tennis_together.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GameUserListServiceTest {

    @Mock
    GameService gameService;
    @Mock
    GameUserListRepository gameUserListRepository;
    @Mock
    UserService userService;
    @InjectMocks
    GameUserListService gameUserListService;

    User user;
    User gameCreator;
    Game game;

    @BeforeEach
    public void before() {
        user = User.builder().uid("testUser").build();
        gameCreator = User.builder().uid("gameCreator").build();
        game = Game.builder()
                .gameNo(1L)
                .gameCreator(gameCreator)
                .gameStatus(GameStatus.RECRUITING)
                .strDt(LocalDate.now())
                .endDt(LocalDate.now())
                .build();
    }

    @Test
    public void game_apply_success() {
        when(gameService.findGameByNo(1L)).thenReturn(game);
        when(gameUserListRepository.findByGameUserAndJoinedGame(user, game)).thenReturn(Optional.empty());

        gameUserListService.applyGame(user, 1L);
    }

    @Test
    public void game_apply_with_not_status_recruiting() {
        game = Game.builder()
                .gameNo(1L)
                .gameCreator(gameCreator)
                .gameStatus(GameStatus.DELETED)
                .strDt(LocalDate.now())
                .endDt(LocalDate.now())
                .build();

        when(gameService.findGameByNo(1L)).thenReturn(game);
        when(gameUserListRepository.findByGameUserAndJoinedGame(user, game)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> gameUserListService.applyGame(user, 1L));
    }

    @Test
    public void game_apply_own() {
        game = Game.builder()
                .gameNo(1L)
                .gameCreator(user)
                .gameStatus(GameStatus.RECRUITING)
                .strDt(LocalDate.now())
                .endDt(LocalDate.now())
                .build();

        when(gameService.findGameByNo(1L)).thenReturn(game);
        when(gameUserListRepository.findByGameUserAndJoinedGame(user, game)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> gameUserListService.applyGame(user, 1L));
    }

    @Test
    public void game_apply_twice() {
        GameUserList gameUserList = GameUserList.builder().build();

        when(gameService.findGameByNo(1L)).thenReturn(game);
        when(gameUserListRepository.findByGameUserAndJoinedGame(user, game)).thenReturn(Optional.of(gameUserList));

        assertThrows(CustomException.class, () -> gameUserListService.applyGame(user, 1L));
    }

    @Test
    public void game_apply_early() {
        game = Game.builder()
                .gameNo(1L)
                .gameCreator(gameCreator)
                .gameStatus(GameStatus.RECRUITING)
                .strDt(LocalDate.now().plusDays(1))
                .endDt(LocalDate.now().plusDays(1))
                .build();

        when(gameService.findGameByNo(1L)).thenReturn(game);
        when(gameUserListRepository.findByGameUserAndJoinedGame(user, game)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> gameUserListService.applyGame(user, 1L));
    }

    @Test
    public void game_apply_late() {
        game = Game.builder()
                .gameNo(1L)
                .gameCreator(gameCreator)
                .gameStatus(GameStatus.RECRUITING)
                .strDt(LocalDate.now().minusDays(1))
                .endDt(LocalDate.now().minusDays(1))
                .build();

        when(gameService.findGameByNo(1L)).thenReturn(game);
        when(gameUserListRepository.findByGameUserAndJoinedGame(user, game)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> gameUserListService.applyGame(user, 1L));
    }

    @Test
    public void approve_applied_game() {
        GameUserList gameUserList = GameUserList.builder()
                .stDvCd(GameUserListStatus.APPLYING).build();

        when(gameService.findGameByGameNoAndGameCreator(1L, gameCreator)).thenReturn(game);
        when(userService.loadUserByUsername(user.getUid())).thenReturn(user);
        when(gameUserListRepository.findByGameUserAndJoinedGame(user, game)).thenReturn(Optional.of(gameUserList));

        gameUserListService.approveAppliedGame(gameCreator, 1L, user.getUid());

        Assertions.assertThat(gameUserList.getStatus()).isEqualTo(GameUserListStatus.APPROVED);
    }

    @Test
    @DisplayName("모집기간이 지난 신청의 승인시 예외 발생")
    public void approve_applied_game_late() {
        GameUserList gameUserList = GameUserList.builder()
                .stDvCd(GameUserListStatus.APPLYING).build();

        game = Game.builder()
                .gameStatus(GameStatus.RECRUITING)
                .strDt(LocalDate.now().minusDays(1))
                .endDt(LocalDate.now().minusDays(1))
                .build();

        when(gameService.findGameByGameNoAndGameCreator(1L, gameCreator)).thenReturn(game);
        when(userService.loadUserByUsername(user.getUid())).thenReturn(user);
        when(gameUserListRepository.findByGameUserAndJoinedGame(user, game)).thenReturn(Optional.of(gameUserList));

        assertThrows(CustomException.class, () -> gameUserListService.approveAppliedGame(gameCreator, 1L, user.getUid()));
    }

    @Test
    @DisplayName("두번 승인 시 예외 발생")
    public void approve_applied_game_twice() {
        GameUserList gameUserList = GameUserList.builder()
                .stDvCd(GameUserListStatus.APPROVED).build();

        when(gameService.findGameByGameNoAndGameCreator(1L, gameCreator)).thenReturn(game);
        when(userService.loadUserByUsername(user.getUid())).thenReturn(user);
        when(gameUserListRepository.findByGameUserAndJoinedGame(user, game)).thenReturn(Optional.of(gameUserList));

        assertThrows(CustomException.class, () -> gameUserListService.approveAppliedGame(gameCreator, 1L, user.getUid()));
    }

    @Test
    @DisplayName("게임에 신청하지 않은 유저를 승인시 예외")
    public void approve_no_applied_game() {
        when(gameService.findGameByGameNoAndGameCreator(1L, gameCreator)).thenReturn(game);
        when(userService.loadUserByUsername(user.getUid())).thenReturn(user);
        when(gameUserListRepository.findByGameUserAndJoinedGame(user, game)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> gameUserListService.approveAppliedGame(gameCreator, 1L, user.getUid()));
    }
}