package kr.couchcoding.tennis_together.controller.court;

import kr.couchcoding.tennis_together.domain.court.model.Court;
import kr.couchcoding.tennis_together.domain.court.service.CourtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courts")
@RequiredArgsConstructor
public class CourtController {

    private final CourtService courtService;

    @GetMapping
    public List<Court> findCourts() {
        return courtService.findCourts();
    }

    @GetMapping("/{courtNo}")
    public Court findCourtByNo(@PathVariable long courtNo) {
        return courtService.findCourtByNo(courtNo);
    }

}
