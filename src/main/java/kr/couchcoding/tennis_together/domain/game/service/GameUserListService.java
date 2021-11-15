package kr.couchcoding.tennis_together.domain.game.service;

import kr.couchcoding.tennis_together.domain.game.dao.GameUserListRepository;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.model.GameUserList;
import kr.couchcoding.tennis_together.domain.game.status.GameStatus;
import kr.couchcoding.tennis_together.domain.game.status.GameUserListStatus;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.domain.user.service.UserService;
import kr.couchcoding.tennis_together.exception.CustomException;
import kr.couchcoding.tennis_together.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class GameUserListService {

    private final GameService gameService;
    private final GameUserListRepository gameUserListRepository;
    private final UserService userService;

    public void applyGame(User user, Long gameNo) {
        Game game = gameService.findGameByNo(gameNo);
        verifyGameForApply(user, game);

        GameUserList newGameUserList = GameUserList.builder()
                .joinedGame(game)
                .gameUser(user)
                .stDvCd(GameUserListStatus.APPLYING)
                .build();

        gameUserListRepository.save(newGameUserList);
    }

    private void verifyGameForApply(User user, Game game) {
        LocalDate now = LocalDate.now();

        if (game.getGameStatus() != GameStatus.RECRUITING)
            throw new CustomException(ErrorCode.BAD_REQUEST_GAME, game.getGameStatus() + " 상태의 게임을 신청할 수 없습니다.");

        if (game.getGameCreator().getUid().equals(user.getUid()))
            throw new CustomException(ErrorCode.BAD_REQUEST_GAME, "자신의 게임에 신청할 수 없습니다.");

        if (!(game.getStrDt().equals(now) || game.getEndDt().equals(now))
                && !(game.getStrDt().isBefore(now) && game.getEndDt().isAfter(now)))
            throw new CustomException(ErrorCode.BAD_REQUEST_GAME, "신청기간이 아닙니다.");

        gameUserListRepository.findByGameUserAndJoinedGame(user, game)
                .ifPresent(gameUserList -> {
                    throw new CustomException(ErrorCode.BAD_REQUEST_GAME, "이미 신청한 게임입니다.");
                });
    }

    public void cancelAppliedGame(User user, Long gameNo) {
        Game game = gameService.findGameByNo(gameNo);

        GameUserList gameUserList = gameUserListRepository.findByGameUserAndJoinedGame(user, game)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST_GAME, "해당 게임에 신청한 이력이 없습니다."));

        LocalDate now = LocalDate.now();

        if (game.getStrDt().equals(now) || game.getEndDt().equals(now) ||
                (game.getStrDt().isBefore(now) && game.getEndDt().isAfter(now))) {
            if (gameUserList.getStatus() == GameUserListStatus.APPROVED)
                game.updateStatus(GameStatus.RECRUITING);
        } else
            throw new CustomException(ErrorCode.BAD_REQUEST_GAME, "신청 기간이 지난 게임은 취소가 불가능 합니다.");

        if (gameUserList.getStatus() == GameUserListStatus.REFUSED)
            throw new CustomException(ErrorCode.BAD_REQUEST_GAME, "거절된 게임의 신청을 취소할 수 없습니다.");

        gameUserListRepository.delete(gameUserList);
    }

    public void approveAppliedGame(User gameCreator, Long gameNo, String joinedUserUid) {
        Game game = gameService.findGameByGameNoAndGameCreator(gameNo, gameCreator);
        User joinedUser = (User) userService.loadUserByUsername(joinedUserUid);

        GameUserList gameUserList = gameUserListRepository.findByGameUserAndJoinedGame(joinedUser, game)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST_GAME, "해당 유저는 게임에 신청한 이력이 없습니다."));

        verifyGameForApprove(game, gameUserList);

        game.updateStatus(GameStatus.CLOSED);
        gameUserList.updateStatus(GameUserListStatus.APPROVED);
    }

    private void verifyGameForApprove(Game game, GameUserList gameUserList) {
        if (game.getGameStatus() != GameStatus.RECRUITING)
            throw new CustomException(ErrorCode.BAD_REQUEST_GAME, game.getGameStatus() + " 상태의 게임의 요청을 승인할 수 없습니다.");

        LocalDate now = LocalDate.now();
        if (!(game.getStrDt().equals(now) || game.getEndDt().equals(now))
                && !(game.getStrDt().isBefore(now) && game.getEndDt().isAfter(now)))
            throw new CustomException(ErrorCode.BAD_REQUEST_GAME, "신청기간이 아닙니다.");

        if (gameUserList.getStatus() == GameUserListStatus.APPROVED)
            throw new CustomException(ErrorCode.BAD_REQUEST_GAME, "이미 승인된 유저입니다.");
    }

    public void refuseAppliedGame(User gameCreator, Long gameNo, String joinedUserUid) {
        Game game = gameService.findGameByGameNoAndGameCreator(gameNo, gameCreator);
        User joinedUser = (User) userService.loadUserByUsername(joinedUserUid);

        GameUserList gameUserList = gameUserListRepository.findByGameUserAndJoinedGame(joinedUser, game)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST_GAME, "해당 유저는 게임에 신청한 이력이 없습니다."));

        LocalDate now = LocalDate.now();
        if (game.getStrDt().equals(now) || game.getEndDt().equals(now) ||
                (game.getStrDt().isBefore(now) && game.getEndDt().isAfter(now))) {
            if (gameUserList.getStatus() == GameUserListStatus.APPROVED)
                game.updateStatus(GameStatus.RECRUITING);
        } else
            throw new CustomException(ErrorCode.BAD_REQUEST_GAME, "신청 기간이 지난 게임은 거절이 불가합니다.");

        gameUserList.updateStatus(GameUserListStatus.REFUSED);
    }

    public Page<GameUserList> findByGameNo(Long gameNo, Pageable pageable) {
        Game joinedGame = gameService.findGameByNo(gameNo);
        return gameUserListRepository.findByJoinedGame(joinedGame, pageable);
    }
}
