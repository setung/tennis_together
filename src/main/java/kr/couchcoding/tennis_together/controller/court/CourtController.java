package kr.couchcoding.tennis_together.controller.court;

import kr.couchcoding.tennis_together.domain.court.dto.CourtDto;
import kr.couchcoding.tennis_together.domain.court.model.Court;
import kr.couchcoding.tennis_together.domain.court.service.CourtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<CourtDto> findCourtByLocCd(@RequestParam long locCdNo, Pageable pageable) {
        return courtService.findCourtByLocCd(locCdNo, pageable).map(court -> new CourtDto(court));
    }
}
