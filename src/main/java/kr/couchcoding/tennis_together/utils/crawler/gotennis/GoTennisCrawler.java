package kr.couchcoding.tennis_together.utils.crawler.gotennis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GoTennisCrawler {

    private static final String BASIC_URL = "http://gotennis.kr/category/seoul,gyeonggi,incheon,gangwon,chungnam,busan,ulsan,daegu,gyeongbuk,uncategorized,youtube_skykim/?tag=indoor-court-aturf,indoor-lesson,indoor-court,indoor-lesson-hard,indoor-court-hard,outdoor-court-aturf,outdoor-court-clay,outdoor-court,outdoor-court-hard";

    private GoTennisCrawler() {
    }

    public static Map<String, GoTennisCourt> crawling() {
        List<String> detailUrls = getCourtDetailUrls();
        Map<String, GoTennisCourt> courts = new HashMap<>();

        for (String url : detailUrls) {
            GoTennisCourt court = getCourt(url);
            courts.put(court.getTitle(), court);
        }

        return courts;
    }

    protected static GoTennisCourt getCourt(String detailUrl) {
        GoTennisCourt court = new GoTennisCourt();

        try {
            Document document = Jsoup.connect(detailUrl).get();
            String title = document.select("header.entry-header").text();
            Elements elements = document.select("td");

            court.setTitle(title);

            for (int i = 0; i < elements.size(); i++) {
                String text = elements.get(i).text();
                if (text.equals("주소") || text.equals("소"))
                    court.setAddress(elements.get(++i).text());
                else if (text.equals("전화"))
                    court.setTel(elements.get(++i).text());
                else if (text.equals("운영시간(실내)"))
                    court.setOperatingTime_in(elements.get(++i).text());
                else if (text.equals("운영시간(야외)"))
                    court.setOperatingTime_out(elements.get(++i).text());
                else if (text.equals("대관료(실내)"))
                    court.setFee_in(elements.get(++i).text());
                else if (text.equals("대관료(야외)"))
                    court.setFee_out(elements.get(++i).text());
                else if (text.equals("홈페이지")) {
                    if (elements.get(++i).childNodeSize() != 0)
                        court.setUrl(elements.get(i).childNode(0).attr("href"));
                    else
                        court.setUrl(elements.get(i).text());
                } else if (text.equals("안내사항"))
                    court.setInstructions(elements.get(++i).text());
            }
        } catch (Exception e) {
            throw new RuntimeException(detailUrl + " " + e.getMessage());
        }
        return court;
    }

    protected static List<String> getCourtDetailUrls() {
        List<String> detailUrls = new ArrayList<>();

        try {
            Document document = Jsoup.connect(BASIC_URL).get();
            Elements elements = document.select("h1.entry-title a");

            for (Element element : elements) {
                detailUrls.add(element.attr("href"));
            }

        } catch (Exception e) {
            throw new RuntimeException("GoTennis 크롤링 실패 " + e.getMessage());
        }

        return detailUrls;
    }

}

