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
import org.springframework.security.core.userdetails.UserDetails;
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

    public void cancelAppliedGame(User user, Long gameNo) {
        Game game = gameService.findGameByNo(gameNo);

        GameUserList gameUserList = gameUserListRepository.findByGameUserAndJoinedGame(user, game)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST_GAME, "해당 게임에 신청한 이력이 없습니다."));

        LocalDate now = LocalDate.now();
        if (((game.getStrDt().equals(now) || game.getEndDt().equals(now) ||
                (game.getStrDt().isBefore(now) && game.getEndDt().isAfter(now))) &&
                gameUserList.getStatus() == GameUserListStatus.APPROVED)) {
            game.updateStatus(GameStatus.RECRUITING);
        }

        // 신청 승락 되기 전엔 자유롭게 취소 가능
        // 근데 신청 승락이 되고 게임은 CLOSED가 되었을떄 취소한다면??? 미리 리뷰도 달았다면?
        //

        gameUserListRepository.delete(gameUserList);
    }

    public void approveAppliedGame(User gameCreator, Long gameNo, String joinedUserUid) {
        Game game = gameService.findGameByGameNoAndGameCreator(gameNo, gameCreator);
        User joinedUser = (User) userService.loadUserByUsername(joinedUserUid);

        if (game.getGameStatus() != GameStatus.RECRUITING)
            throw new CustomException(ErrorCode.BAD_REQUEST_GAME, game.getGameStatus() + " 상태의 게임의 요청을 승락할 수 없습니다.");

        GameUserList gameUserList = gameUserListRepository.findByGameUserAndJoinedGame(joinedUser, game)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST_GAME, "해당 유저는 게임에 신청한 이력이 없습니다."));

        game.updateStatus(GameStatus.CLOSED);
        gameUserList.updateStatus(GameUserListStatus.APPROVED);
    }
}
