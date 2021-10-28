package kr.couchcoding.tennis_together.utils.crawler.gotennis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GoTennisCrawlerTest {

    @Test
    void getDetailUrl() {
        List<String> detailUrls = GoTennisCrawler.getCourtDetailUrls();

        for (String url : detailUrls) {
            System.out.println(url);
        }
    }

    @Test
    public void getCourt() {
        GoTennisCourt court = GoTennisCrawler.getCourt("http://gotennis.kr/seoul/7616/");
        System.out.println(court);
    }

    @Test
    public void crawling() {
        Map<String, GoTennisCourt> result = GoTennisCrawler.crawling();

        for (String key : result.keySet()) {
            System.out.println(result.get(key));
        }
    }
}