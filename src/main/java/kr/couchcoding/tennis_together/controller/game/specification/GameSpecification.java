package kr.couchcoding.tennis_together.controller.game.specification;

import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.game.status.GameStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class GameSpecification {

    public static Specification<Game> equalCourtNo(long courtNo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("court"), courtNo);
    }

    public static Specification<Game> equalUid(String uid) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("gameCreator").get("uid"), uid);
    }

    public static Specification<Game> equalGenderType(String genderType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("genderType"), genderType);
    }

    public static Specification<Game> equalAgeType(int ageType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ageType"), ageType);
    }

    public static Specification<Game> equalHistoryType(int historyType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("historyType"), historyType);
    }

    public static Specification<Game> equalStatus(GameStatus status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("gameStatus"), status);
    }

    public static Specification<Game> notDelete() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("gameStatus"), GameStatus.DELETED);
    }

    public static Specification<Game> geStrDt(LocalDate strDt) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("strDt"), strDt);
    }

    public static Specification<Game> leEndDt(LocalDate endDt) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("endDt"), endDt);
    }

    public static Specification<Game> geScore(Double score) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("gameCreator").get("score"), score);
    }

    public static Specification<Game> equalLocSkk(String locSkk) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("court").get("locCd").get("locSkk"), locSkk);
    }

    public static Specification<Game> equalLocSd(String locSd) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("court").get("locCd").get("locSd"), locSd);
    }
}
