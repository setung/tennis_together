package kr.couchcoding.tennis_together.domain.dao;

import kr.couchcoding.tennis_together.entity.CourtInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourtRepository extends JpaRepository<CourtInfo, Long> {
}
