package kr.couchcoding.tennis_together.utils.crawler.seoultennis;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SeoulTennisCrawlerTest {

    @Test
    void getCourtDetailUrls() {
        List<String> courtDetailUrls = SeoulTennisCrawler.getCourtDetailUrls();

        for (String courtDetailUrl : courtDetailUrls) {
            System.out.println("courtDetailUrl = " + courtDetailUrl);
        }

        Assertions.assertThat(courtDetailUrls.size()).isEqualTo(131);
    }

    @Test
    public void getCourt() {
        SeoulTennisCourt court = SeoulTennisCrawler.getCourt("https://yeyak.seoul.go.kr/web/reservation/selectReservView.do?rsv_svc_id=S211022090525417357");
        System.out.println(court);
    }

    @Test
    public void crawling() {
        Map<String, SeoulTennisCourt> result = SeoulTennisCrawler.crawling();
        for (String key : result.keySet()) {
            System.out.println(result.get(key));
        }
    }
}