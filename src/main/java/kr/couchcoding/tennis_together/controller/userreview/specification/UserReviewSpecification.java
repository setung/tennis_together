package kr.couchcoding.tennis_together.controller.userreview.specification;

import kr.couchcoding.tennis_together.domain.userreview.model.UserReview;
import org.springframework.data.jpa.domain.Specification;

public class UserReviewSpecification {

    public static Specification<UserReview> equalUid(String uid) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("gameCreator").get("uid"), uid);
    }

    public static Specification<UserReview> equalWriteUser(String writtenUserUid) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("writtenUser").get("uid"), writtenUserUid);
    }

    public static Specification<UserReview> equalRecipient(String recipientUid) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("recipient").get("uid"), recipientUid);
    }

    public static Specification<UserReview> equalGameNo(Long gameNo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("game").get("gameNo"), gameNo);
    }

    public static Specification<UserReview> equalScore(Long score) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("score"), score);
    }
}
