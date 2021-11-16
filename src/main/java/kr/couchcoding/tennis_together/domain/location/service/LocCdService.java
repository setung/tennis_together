package kr.couchcoding.tennis_together.domain.location.service;

import kr.couchcoding.tennis_together.domain.location.dao.LocCdRepository;
import kr.couchcoding.tennis_together.domain.location.model.LocCd;
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
public class LocCdService {

    private final LocCdRepository locCdRepository;

    public Page<LocCd> findAllLocCd(Specification<LocCd> spec, Pageable pageable) {
        Page<LocCd> locCdPage = locCdRepository.findAll(spec, pageable);

        if (locCdPage.getTotalElements() == 0)
            throw new CustomException(ErrorCode.NOT_FOUND_LOCATION);

        return locCdRepository.findAll(spec, pageable);
    }

    public LocCd findById(long locCdNo) {
        Optional<LocCd> locCd = locCdRepository.findById(locCdNo);
        return locCd.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_LOCATION));
    }


    public LocCd findByLocSdAndLocSkk (String locSd, String locSkk){
        Optional<LocCd> locCd = locCdRepository.findByLocSdAndLocSkk(locSd, locSkk);
        return locCd.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_LOCATION));
    }
}
