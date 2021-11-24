package kr.couchcoding.tennis_together.controller.location;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.couchcoding.tennis_together.controller.location.dto.LocCdDTO;
import kr.couchcoding.tennis_together.controller.location.specification.LocCdSpecification;
import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import kr.couchcoding.tennis_together.domain.location.service.LocCdService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
@Api(tags = {"위치정보 관련 API"})
public class LocCdController {

    private final LocCdService locCdService;

    @ApiOperation(value = "위치정보 전체 조회 API")
    @GetMapping
    public Page<LocCdDTO> findAllLocCd(
            @RequestParam(value = "locSd", required = false) String locSd,
            @RequestParam(value = "locSkk", required = false) String locSkk,
            @RequestParam(value = "locSdName", required = false) String locSdName,
            @RequestParam(value = "locSkkName", required = false) String locSkkName,
            @PageableDefault(sort = "locCdNo") Pageable pageable) {

        Specification<LocCd> spec = (root, query, criteriaBuilder) -> null;

        if (locSd != null) spec = spec.and(LocCdSpecification.equalLocSd(locSd));
        if (locSkk != null) spec = spec.and(LocCdSpecification.equalLocSkk(locSkk));
        if (locSdName != null) spec = spec.and(LocCdSpecification.likeLocSdName(locSdName));
        if (locSkkName != null) spec = spec.and(LocCdSpecification.likeLocSkkName(locSkkName));

        return locCdService.findAllLocCd(spec, pageable).map(locCd -> new LocCdDTO(locCd));
    }

    @ApiOperation(value = "위치정보 상세조회 API")
    @GetMapping("/{locCdNo}")
    public LocCdDTO findByLocCdNo(@PathVariable long locCdNo) {
        return new LocCdDTO(locCdService.findById(locCdNo));
    }
}
