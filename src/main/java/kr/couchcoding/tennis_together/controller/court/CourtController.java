package kr.couchcoding.tennis_together.controller.court;

import kr.couchcoding.tennis_together.controller.court.specification.CourtSpecification;
import kr.couchcoding.tennis_together.domain.court.dto.CourtDto;
import kr.couchcoding.tennis_together.domain.court.model.Court;
import kr.couchcoding.tennis_together.domain.court.service.CourtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courts")
@RequiredArgsConstructor
public class CourtController {

    private final CourtService courtService;

    @GetMapping("/{courtNo}")
    public CourtDto findCourtByNo(@PathVariable long courtNo) {
        Court court = courtService.findCourtByNo(courtNo);
        return new CourtDto(court);
    }

    @GetMapping
    public Page<CourtDto> findAll(@RequestParam(required = false) Long locCdNo,
                                  @RequestParam(required = false) String locSd,
                                  @RequestParam(required = false) String locSkk,
                                  Pageable pageable) {
        Specification<Court> spec = (root, query, criteriaBuilder) -> null;

        if (locCdNo != null) spec = spec.and(CourtSpecification.equalsLocCdNo(locCdNo));
        if (locSd != null) spec = spec.and(CourtSpecification.equalsLocSd(locSd));
        if (locSkk != null) spec = spec.and(CourtSpecification.equalsLocSkk(locSkk));

        return courtService.findAll(spec, pageable).map(court -> new CourtDto(court));
    }
}
