package kr.couchcoding.tennis_together.controller.court;

import io.swagger.annotations.*;
import kr.couchcoding.tennis_together.controller.court.specification.CourtSpecification;
import kr.couchcoding.tennis_together.domain.court.dto.CourtDto;
import kr.couchcoding.tennis_together.domain.court.model.Court;
import kr.couchcoding.tennis_together.domain.court.service.CourtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"테니스장 정보를 제공하는 API"})
@RestController
@RequestMapping("/courts")
@RequiredArgsConstructor
public class CourtController {

    private final CourtService courtService;

    @ApiOperation(value = "테니스장 아이디를 통해 조회하는 API")
    @ApiResponses({@ApiResponse(code = 400, message = "잘못된 파라미터 입력시"),
            @ApiResponse(code = 404, message = "테니스장 데이터가 없을시")})
    @GetMapping("/{courtNo}")
    public CourtDto findCourtByNo(@ApiParam(value = "테니스장 아이디") @PathVariable long courtNo) {
        Court court = courtService.findCourtByNo(courtNo);
        return new CourtDto(court);
    }

    @ApiOperation(value = "테니스장 전체 조회하는 API")
    @GetMapping
    public Page<CourtDto> findAll(@ApiParam(value = "위치정보 아이디") @RequestParam(required = false) Long locCdNo,
                                  @ApiParam(value = "위치 시도") @RequestParam(required = false) String locSd,
                                  @ApiParam(value = "위치 시군구") @RequestParam(required = false) String locSkk,
                                  Pageable pageable) {
        Specification<Court> spec = (root, query, criteriaBuilder) -> null;

        if (locCdNo != null) spec = spec.and(CourtSpecification.equalsLocCdNo(locCdNo));
        if (locSd != null) spec = spec.and(CourtSpecification.equalsLocSd(locSd));
        if (locSkk != null) spec = spec.and(CourtSpecification.equalsLocSkk(locSkk));

        return courtService.findAll(spec, pageable).map(court -> new CourtDto(court));
    }
}
