package kr.couchcoding.tennis_together.domain.court.repository;

import kr.couchcoding.tennis_together.domain.court.model.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long>, JpaSpecificationExecutor<Court> {
}
