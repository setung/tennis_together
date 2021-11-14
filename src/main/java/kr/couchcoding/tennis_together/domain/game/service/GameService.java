package kr.couchcoding.tennis_together.domain.game.service;

import kr.couchcoding.tennis_together.controller.game.dto.RequestGameDTO;
import kr.couchcoding.tennis_together.domain.court.model.Court;
import kr.couchcoding.tennis_together.domain.court.service.CourtService;
import kr.couchcoding.tennis_together.domain.game.dao.GameRepository;
import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.status.GameStatus;
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

    public Game postGame(User user, RequestGameDTO postGameDTO) {
        Court court = courtService.findCourtByNo(postGameDTO.getCourtNo());

        Game game = Game.builder()
                .gameCreator(user)
                .court(court)
                .title(postGameDTO.getTitle())
                .content(postGameDTO.getContent())
                .historyType(postGameDTO.getHistoryType())
                .ageType(postGameDTO.getAgeType())
                .genderType(postGameDTO.getGenderType())
                .strDt(postGameDTO.getStrDt())
                .endDt(postGameDTO.getEndDt())
                .gameStatus(GameStatus.RECRUITING)
                .build();

        return gameRepository.save(game);
    }

    public Game findGameByGameNoAndGameCreator(Long gameNo, User gameCreator) {
        Optional<Game> gameOpt = gameRepository.findGameByGameNoAndGameCreator(gameNo, gameCreator);

        gameOpt.ifPresentOrElse(
                game -> {
                    if (game.getGameStatus() == GameStatus.DELETED)
                        throw new CustomException(ErrorCode.BAD_REQUEST_GAME, "삭제된 Game 입니다.");
                },
                () -> {
                    throw new CustomException(ErrorCode.NOT_FOUND_GAME);
                });

        return gameOpt.get();
    }

    public void updateGame(User user, Long gameNo, RequestGameDTO updatedGameDTO) {
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

    public void deleteGame(User user, long gameNo) {
        Game game = findGameByGameNoAndGameCreator(gameNo, user);

        if (game.getGameStatus() == GameStatus.DELETED)
            throw new CustomException(ErrorCode.BAD_REQUEST_GAME, "이미 삭제된 게임입니다.");

        game.updateStatus(GameStatus.DELETED);
    }

    public Game findGameByNo(Long gameNo) {
        Optional<Game> gameOpt = gameRepository.findById(gameNo);

        gameOpt.ifPresentOrElse(
                game -> {
                    if (game.getGameStatus() == GameStatus.DELETED)
                        throw new CustomException(ErrorCode.BAD_REQUEST_GAME, "삭제된 Game 입니다.");
                },
                () -> {
                    throw new CustomException(ErrorCode.NOT_FOUND_GAME);
                });

        return gameOpt.get();
    }


    //삭제하기
    public Game findGameByGameNo(Long gameNo) {
        return gameRepository.findGameByGameNo(gameNo); }

}
