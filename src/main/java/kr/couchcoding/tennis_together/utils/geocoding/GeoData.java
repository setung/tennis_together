package kr.couchcoding.tennis_together.utils.geocoding;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GeoData {

    private String status;
    private Meta meta;
    private Address[] addresses;

    @Getter
    @ToString
    static class Meta {
        private int totalCount;
        private int page;
        private int count;
    }

    @Getter
    @ToString
    public static class Address {
        private String roadAddress;
        private String jibunAddress;
        private String englishAddress;
        private double x;
        private double y;
        private float distance;
    }

}

