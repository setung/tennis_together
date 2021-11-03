package kr.couchcoding.tennis_together.domain.court.repository;

import kr.couchcoding.tennis_together.domain.court.model.Court;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourtRepository extends JpaRepository<Court, Long> {
}
