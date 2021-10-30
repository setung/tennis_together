package kr.couchcoding.tennis_together.utils.geocoding;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public final class Geocoding {

    public static GeoData getGeoDataByAddress(String completeAddress) {
        String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + completeAddress;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.set("X-NCP-APIGW-API-KEY-ID", "1oukiuqi10");
        headers.set("X-NCP-APIGW-API-KEY", "HWoRAEYDSH7HuIqSomhAMdJTjsUeh15ox4Br696i");

        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<GeoData> response = restTemplate.exchange(url, HttpMethod.GET, request, GeoData.class);

        return response.getBody();
    }

    public static LatLonData getLatLon(String completeAddress) {
        GeoData geoData = getGeoDataByAddress(completeAddress);
        GeoData.Address[] addresses = geoData.getAddresses();

        if (addresses.length != 0) {
            return new LatLonData(addresses[0].getX(), addresses[0].getY());
        }
        return new LatLonData(0, 0);
    }

}
