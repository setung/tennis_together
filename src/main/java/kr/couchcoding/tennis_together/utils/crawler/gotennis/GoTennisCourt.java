package kr.couchcoding.tennis_together.utils.crawler.gotennis;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoTennisCourt {

    private String title;
    private String address;
    private String tel;
    private String operatingTime_in;
    private String operatingTime_out;
    private String fee_in;
    private String fee_out;
    private String instructions;
    private String url;

}
