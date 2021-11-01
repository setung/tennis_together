package kr.couchcoding.tennis_together.domain.service;

import kr.couchcoding.tennis_together.domain.dao.CourtRepository;
import kr.couchcoding.tennis_together.entity.CourtInfo;
import kr.couchcoding.tennis_together.exception.CustomException;
import kr.couchcoding.tennis_together.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourtService {

    private final CourtRepository courtRepository;

    public List<CourtInfo> findCourts() {
        return courtRepository.findAll();
    }

    public CourtInfo findCourtByNo(long courtNo) {
        Optional<CourtInfo> courtInfo = courtRepository.findById(courtNo);

        if (courtInfo.isPresent())
            return courtInfo.get();
        else
            throw new CustomException(ErrorCode.NOT_FOUND_GAME_COURT);
    }
}
