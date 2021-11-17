package kr.couchcoding.tennis_together.domain.userreview.dao;

import kr.couchcoding.tennis_together.domain.game.model.Game;
import kr.couchcoding.tennis_together.domain.user.model.User;
import kr.couchcoding.tennis_together.domain.userreview.model.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReviewRepository extends JpaRepository<UserReview, Long>, JpaSpecificationExecutor<UserReview> {

    Optional<UserReview> findByWrittenUserAndGame(User writer, Game game);

    @Query("SELECT AVG(u.score) FROM UserReview u GROUP BY u.recipient HAVING recipient = :recipient")
    Double getAverageScore(@Param("recipient") User recipient);
}
