package kr.couchcoding.tennis_together.domain.game.service;

import kr.couchcoding.tennis_together.domain.game.dao.GameRepository;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.exception.CustomException;
import kr.couchcoding.tennis_together.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public void postGame(Game game) {
        gameRepository.save(game);
    }

    public Page<Game> findAll(Specification<Game> spec, Pageable pageable) {
        Page<Game> games = gameRepository.findAll(spec, pageable);

        if (games.isEmpty())
            throw new CustomException(ErrorCode.NOT_FOUND_GAME);

        return games;
    }
}
