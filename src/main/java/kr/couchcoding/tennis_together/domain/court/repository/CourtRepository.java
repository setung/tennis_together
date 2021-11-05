package kr.couchcoding.tennis_together.domain.court.repository;

import kr.couchcoding.tennis_together.domain.court.model.Court;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourtRepository extends JpaRepository<Court, Long> {

    @Query("SELECT c FROM Court c WHERE c.locCd.locCdNo = :locCdNo")
    Page<Court> findByLocCd(@Param("locCdNo") long locCdNo, Pageable pageable);
}
