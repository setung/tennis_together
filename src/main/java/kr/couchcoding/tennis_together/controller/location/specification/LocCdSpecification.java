package kr.couchcoding.tennis_together.controller.location.specification;

import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import org.springframework.data.jpa.domain.Specification;

public class LocCdSpecification {

    public static Specification<LocCd> equalLocSd(String locSd) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("locSd"), locSd);
    }

    public static Specification<LocCd> equalLocSkk(String locSkk) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("locSkk"), locSkk);
    }

    public static Specification<LocCd> likeLocSdName(String locSdName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("locSdName"), "%" + locSdName + "%");
    }

    public static Specification<LocCd> likeLocSkkName(String locSkkName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("locSkkName"), "%" + locSkkName + "%");
    }
}
