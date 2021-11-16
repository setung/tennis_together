package kr.couchcoding.tennis_together.controller.game.specification;

import kr.couchcoding.tennis_together.domain.game.model.GameUserList;
import kr.couchcoding.tennis_together.domain.game.status.GameUserListStatus;
import org.springframework.data.jpa.domain.Specification;

public class GameUserListSpecification {

    public static Specification<GameUserList> equalGender(String gender) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("gameUser").get("gender"), gender);
    }

    public static Specification<GameUserList> equalHistory(Integer history) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("gameUser").get("history"), history);
    }

    public static Specification<GameUserList> equalScore(Long score) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("gameUser").get("score"), score);
    }

    public static Specification<GameUserList> equalStatus(GameUserListStatus status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<GameUserList> equalGameNo(Long gameNo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("joinedGame").get("gameNo"), gameNo);
    }
}
