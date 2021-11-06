package kr.couchcoding.tennis_together.domain.location.dao;

import kr.couchcoding.tennis_together.domain.location.model.LocCd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LocCdRepository extends JpaRepository<LocCd, Long>, JpaSpecificationExecutor<LocCd> {

}
