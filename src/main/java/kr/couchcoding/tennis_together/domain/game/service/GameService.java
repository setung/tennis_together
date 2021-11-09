package kr.couchcoding.tennis_together.domain.game.service;

import kr.couchcoding.tennis_together.domain.game.dao.GameRepository;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.exception.CustomException;
import kr.couchcoding.tennis_together.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public void postGame(Game game) {
        gameRepository.save(game);
    }

    public Game findGameByGameNoAndGameCreator(Long gameNo, User gameCreator) {
        Optional<Game> game = gameRepository.findGameByGameNoAndGameCreator(gameNo, gameCreator);
        return game.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_GAME));
    }

    public void updateGame(Game game, Game updatedGame) {
        game.updateGame(updatedGame);
    }

    public Page<Game> findAll(Specification<Game> spec, Pageable pageable) {
        Page<Game> games = gameRepository.findAll(spec, pageable);

        if (games.isEmpty())
            throw new CustomException(ErrorCode.NOT_FOUND_GAME);

        return games;
    }
}
