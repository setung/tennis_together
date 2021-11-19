package kr.couchcoding.tennis_together.controller.game.specification;

import kr.couchcoding.tennis_together.domain.game.model.GameUserList;
import kr.couchcoding.tennis_together.domain.game.status.GameStatus;
import kr.couchcoding.tennis_together.domain.game.status.GameUserListStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

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

    public static Specification<GameUserList> equalGameStatus(GameStatus gameStatus) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("joinedGame").get("gameStatus"), gameStatus);
    }

    public static Specification<GameUserList> notEqualGameStatus(GameStatus gameStatus) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("joinedGame").get("gameStatus"), gameStatus);
    }

    public static Specification<GameUserList> lessThanEndDt(LocalDate endDt) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("joinedGame").get("endDt"), endDt);
    }

    public static Specification<GameUserList> equalGameNo(Long gameNo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("joinedGame").get("gameNo"), gameNo);
    }

    public static Specification<GameUserList> equalGameCreator(String gameCreatorUid) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("joinedGame").get("gameCreator").get("uid"), gameCreatorUid);
    }

    public static Specification<GameUserList> equalGameUser(String GameUserUid) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("gameUser").get("uid"), GameUserUid);
    }
}
