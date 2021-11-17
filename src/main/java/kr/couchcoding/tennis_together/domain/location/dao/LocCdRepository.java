package kr.couchcoding.tennis_together.domain.location.dao;

import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocCdRepository extends JpaRepository<LocCd, Long>, JpaSpecificationExecutor<LocCd> {
    Optional<LocCd> findByLocSdAndLocSkk(String locSd, String locSkk);
}
