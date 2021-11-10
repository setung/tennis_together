package kr.couchcoding.tennis_together.domain.game.service;

import kr.couchcoding.tennis_together.controller.game.dto.PostGameDTO;
import kr.couchcoding.tennis_together.domain.court.model.Court;
import kr.couchcoding.tennis_together.domain.court.service.CourtService;
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

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GameService {

    private final GameRepository gameRepository;
    private final CourtService courtService;

    public void postGame(Game game) {
        gameRepository.save(game);
    }

    public Game findGameByGameNoAndGameCreator(Long gameNo, User gameCreator) {
        Optional<Game> game = gameRepository.findGameByGameNoAndGameCreator(gameNo, gameCreator);
        return game.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_GAME));
    }

    public void updateGame(User user, Long gameNo, PostGameDTO updatedGameDTO) {
        Game game = findGameByGameNoAndGameCreator(gameNo, user);
        Court court = null;

        if (updatedGameDTO.getCourtNo() != null)
            court = courtService.findCourtByNo(updatedGameDTO.getCourtNo());

        Game updatedGame = Game.builder()
                .title(updatedGameDTO.getTitle())
                .content(updatedGameDTO.getContent())
                .historyType(updatedGameDTO.getHistoryType())
                .genderType(updatedGameDTO.getGenderType())
                .ageType(updatedGameDTO.getAgeType())
                .strDt(updatedGameDTO.getStrDt())
                .endDt(updatedGameDTO.getEndDt())
                .court(court)
                .build();

        game.updateGame(updatedGame);
    }

    public Page<Game> findAll(Specification<Game> spec, Pageable pageable) {
        Page<Game> games = gameRepository.findAll(spec, pageable);

        if (games.isEmpty())
            throw new CustomException(ErrorCode.NOT_FOUND_GAME);

        return games;
    }

    public void deleteGame(Game game) {
        gameRepository.delete(game);
    }
}
