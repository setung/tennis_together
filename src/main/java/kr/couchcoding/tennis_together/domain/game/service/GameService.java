package kr.couchcoding.tennis_together.domain.game.service;

import kr.couchcoding.tennis_together.domain.game.dao.GameRepository;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public void postGame(Game game) {
        gameRepository.save(game);
    }
}
