package kr.couchcoding.tennis_together.domain.user_review.dao;

import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.domain.user_review.model.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserReviewRepository extends JpaRepository<UserReview, Long> {
    Optional<UserReview> findByWrittenUserAndGame(User writer, Game game);

    @Query("SELECT AVG(u.score) FROM UserReview u GROUP BY u.recipient HAVING recipient = :recipient")
    Double getAverageScore(@Param("recipient") User recipient);
}
