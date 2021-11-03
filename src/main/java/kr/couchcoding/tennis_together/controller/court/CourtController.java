package kr.couchcoding.tennis_together.controller.court;

import kr.couchcoding.tennis_together.domain.court.model.Court;
import kr.couchcoding.tennis_together.domain.court.service.CourtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courts")
@RequiredArgsConstructor
public class CourtController {

    private final CourtService courtService;

    @GetMapping("/{courtNo}")
    public Court findCourtByNo(@PathVariable long courtNo) {
        return courtService.findCourtByNo(courtNo);
    }

    @GetMapping
    public List<Court> findCourtByLocCd(@RequestParam long locCdNo) {
        return courtService.findCourtByLocCd(locCdNo);
    }
}
