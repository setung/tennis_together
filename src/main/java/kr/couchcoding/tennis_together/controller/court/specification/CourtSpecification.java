package kr.couchcoding.tennis_together.controller.court.specification;

import kr.couchcoding.tennis_together.domain.court.model.Court;
import org.springframework.data.jpa.domain.Specification;

public class CourtSpecification {

    public static Specification<Court> equalsLocCdNo(long locCdNo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("locCd").get("locCdNo"), locCdNo);
    }
}
