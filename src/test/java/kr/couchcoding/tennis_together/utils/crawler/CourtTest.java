package kr.couchcoding.tennis_together.utils.crawler;

import kr.couchcoding.tennis_together.utils.crawler.gotennis.GoTennisCourt;
import kr.couchcoding.tennis_together.utils.crawler.gotennis.GoTennisCrawler;
import kr.couchcoding.tennis_together.utils.crawler.seoultennis.SeoulTennisCourt;
import kr.couchcoding.tennis_together.utils.crawler.seoultennis.SeoulTennisCrawler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Map;

@SpringBootTest
@Transactional
@Rollback(value = false)
class CourtTest {

    @Autowired
    EntityManager em;

    @Test
    public void seoulTennisToCourt() {
        Map<String, SeoulTennisCourt> seoulTennis = SeoulTennisCrawler.crawling();

        for (String key : seoulTennis.keySet()) {
            Court court = new Court(seoulTennis.get(key));
            em.persist(court);
        }
    }

    @Test
    public void goTennisToCourt() {
        Map<String, GoTennisCourt> goTennis = GoTennisCrawler.crawling();

        for (String key : goTennis.keySet()) {
            Court court = new Court(goTennis.get(key));
            em.persist(court);
        }
    }

}

