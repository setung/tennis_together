package kr.couchcoding.tennis_together.domain.game.service;

import kr.couchcoding.tennis_together.domain.game.dao.GameUserListRepository;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.model.GameUserList;
import kr.couchcoding.tennis_together.domain.game.status.GameStatus;
import kr.couchcoding.tennis_together.domain.game.status.GameUserListStatus;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.exception.CustomException;
import kr.couchcoding.tennis_together.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class GameUserListService {

    private final GameService gameService;
    private final GameUserListRepository gameUserListRepository;

    public void applyGame(User user, Long gameNo) {
        Game game = gameService.findGameByNo(gameNo);
        verifyGame(user, game);

        GameUserList newGameUserList = GameUserList.builder()
                .joinedGame(game)
                .gameUser(user)
                .stDvCd(GameUserListStatus.APPLYING)
                .build();

        gameUserListRepository.save(newGameUserList);
    }

    private void verifyGame(User user, Game game) {
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
}
