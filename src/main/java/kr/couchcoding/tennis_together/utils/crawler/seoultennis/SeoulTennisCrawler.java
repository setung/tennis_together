package kr.couchcoding.tennis_together.utils.crawler.seoultennis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SeoulTennisCrawler {

    private static final String BASE_URL = "https://yeyak.seoul.go.kr/web/search/selectPageListDetailSearchImg.do?&code=T100&dCode=T108&currentPage=";
    private static final String DETAIL_URL = "https://yeyak.seoul.go.kr/web/reservation/selectReservView.do?rsv_svc_id=";

    private SeoulTennisCrawler() {
    }

    public static Map<String, SeoulTennisCourt> crawling() {
        List<String> detailUrls = getCourtDetailUrls();
        Map<String, SeoulTennisCourt> courts = new HashMap<>();

        for (String detailUrl : detailUrls) {
            SeoulTennisCourt courtData = getCourt(detailUrl);
            courts.put(courtData.getName(), courtData);
        }

        return courts;
    }

    protected static SeoulTennisCourt getCourt(String detailUrl) {
        SeoulTennisCourt court;

        try {
            Document document = Jsoup.connect(detailUrl).get();

            Elements elementsAddressTel = document.select("div.tab_con div.sub_each p.sub_txt1");
            String address = elementsAddressTel.get(4).text();
            String tel = elementsAddressTel.get(5).text();

            Elements elements_detail = document.select("div.sub_detail");
            String instructions = elements_detail.get(0).text();

            Elements elements_li = document.select("div.con_box ul.dt_top_list li");
            String name = elements_li.get(1).ownText();
            String fee = elements_li.get(8).ownText();

            court = new SeoulTennisCourt(name, address, tel, fee, instructions, detailUrl);

        } catch (Exception e) {
            throw new RuntimeException(detailUrl + " " + e);
        }

        return court;
    }

    protected static List<String> getCourtDetailUrls() {
        List<String> detailUrls = new ArrayList<>();
        int page = 1;

        try {
            while (true) {
                Document document = Jsoup.connect(BASE_URL + (page++)).get();
                Elements elements = document.select("ul.img_board li a");

                if (elements.isEmpty()) break;

                for (Element element : elements) {
                    detailUrls.add(DETAIL_URL + extractCourtCode(element.attr("onclick")));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("SeoulTennisCrawler 실패 " + e.getMessage());
        }

        return detailUrls;
    }

    private static String extractCourtCode(String code) {
        return code.substring(14, 33);
    }
}
