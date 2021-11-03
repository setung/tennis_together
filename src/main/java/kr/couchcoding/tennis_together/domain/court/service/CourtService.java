package kr.couchcoding.tennis_together.domain.court.service;

import kr.couchcoding.tennis_together.domain.court.model.Court;
import kr.couchcoding.tennis_together.domain.court.repository.CourtRepository;
import kr.couchcoding.tennis_together.exception.CustomException;
import kr.couchcoding.tennis_together.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CourtService {

    private final CourtRepository courtRepository;

    public List<Court> findCourts() {
        return courtRepository.findAll();
    }

    public Court findCourtByNo(long courtNo) {
        Optional<Court> courtInfo = courtRepository.findById(courtNo);

        if (courtInfo.isPresent())
            return courtInfo.get();
        else
            throw new CustomException(ErrorCode.NOT_FOUND_GAME_COURT);
    }

    public Court findCourtByLocCd(long locCdNo) {
        Optional<Court> courtInfo = courtRepository.findByLocCd(locCdNo);

        if (courtInfo.isPresent())
            return courtInfo.get();
        else
            throw new CustomException(ErrorCode.NOT_FOUND_GAME_COURT);
    }
}
