package kr.couchcoding.tennis_together.controller;

import kr.couchcoding.tennis_together.domain.service.CourtService;
import kr.couchcoding.tennis_together.entity.CourtInfo;
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
    public List<CourtInfo> findCourts() {
        return courtService.findCourts();
    }

    @GetMapping("/{courtNo}")
    public CourtInfo findCourtByNo(@PathVariable long courtNo) {
        return courtService.findCourtByNo(courtNo);
    }

}
