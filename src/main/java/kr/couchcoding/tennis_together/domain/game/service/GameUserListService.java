package kr.couchcoding.tennis_together.domain.game.service;

import kr.couchcoding.tennis_together.domain.game.dao.GameUserListRepository;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.model.GameUserList;
import kr.couchcoding.tennis_together.domain.game.status.GameUserListStatus;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.exception.CustomException;
import kr.couchcoding.tennis_together.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GameUserListService {

    private final GameService gameService;
    private final GameUserListRepository gameUserListRepository;

    public void applyGame(User user, Long gameNo) {
        Game game = gameService.findGameByNo(gameNo);

        if (game.getGameCreator().getUid().equals(user.getUid()))
            throw new CustomException(ErrorCode.BAD_REQUEST_GAME, "자신의 게임에 신청할 수 없습니다.");

        gameUserListRepository.findByGameUserAndJoinedGame(user, game)
                .ifPresent(gameUserList -> {
                    throw new CustomException(ErrorCode.BAD_REQUEST_GAME, "이미 신청한 게임입니다.");
                });

        GameUserList newGameUserList = GameUserList.builder()
                .joinedGame(game)
                .gameUser(user)
                .stDvCd(GameUserListStatus.APPLYING)
                .build();

        gameUserListRepository.save(newGameUserList);
    }
}
