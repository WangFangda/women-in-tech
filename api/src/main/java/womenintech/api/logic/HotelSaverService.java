package womenintech.api.logic;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import womenintech.api.config.BookingApiConfiguration;
import womenintech.api.datamodel.Hotel;
import womenintech.api.repo.HotelRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author fangda.wang
 */
@Service
public class HotelSaverService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpHeaders httpHeaders;
    @Autowired
    private BookingApiConfiguration apiConfiguration;
    @Autowired
    private HotelRepository hRepo;

    @AllArgsConstructor
    private enum Params {
        CITY_IDS_HOLDER("CITY_IDS"),
        DISTRICT_IDS_HODLER("DISTRICT_IDS"),
        HOTEL_IDS_HOLDER("HOTEL_IDS"),
        HOTEL_ID("hotel_id"),
        LOCATION("location"),
        NAME("name"),
        LAT("latitude"),
        LNG("longitude");
        public final String value;
    }

    public void save(String districts, String city) {
        val request = new HttpEntity<String>(httpHeaders);

        val hotelIds = getHotelIds(request, districts, city);

        for (int i = 0; i < hotelIds.size(); i=i+10) {
            String ids = hotelIds.get(i);
            for (int y = i+1; y < i+10; y++) {
                if (y == hotelIds.size())
                    break;
                ids += ",";
                ids += hotelIds.get(y);
            }
            batchSave(request, ids);
        }
    }

    private List<String> getHotelIds(HttpEntity<String> request, String districts, String city) {
        val districtHotelUrl = apiConfiguration.getDistrictHotelUrl.replaceAll(Params.CITY_IDS_HOLDER.value, city).replaceAll(Params.DISTRICT_IDS_HODLER.value, districts);
        val districtHotelResponse = restTemplate.exchange(districtHotelUrl, HttpMethod.GET, request, List.class);
        val districtHotelBody = (List<LinkedHashMap<String, Object>>) districtHotelResponse.getBody();

        List<String> hotelIds = new ArrayList<>();
        for (LinkedHashMap<String, Object> record: districtHotelBody) {
            hotelIds.add((String)record.get(Params.HOTEL_ID.value));
        }
        return hotelIds;
    }

    private void batchSave(HttpEntity<String> request, String hotel_ids) {
        val hotelUrl = apiConfiguration.getHotelUrl.replaceAll(Params.HOTEL_IDS_HOLDER.value, hotel_ids);
        val hotelResponse = restTemplate.exchange(hotelUrl, HttpMethod.GET, request, List.class);
        val hotelBody = (List<LinkedHashMap<String, Object>>) hotelResponse.getBody();

        List<Hotel> hotels = new ArrayList<>();
        for (LinkedHashMap<String, Object> record: hotelBody) {
            val id = Long.parseLong((String)record.get(Params.HOTEL_ID.value));
            val name = (String)record.get(Params.NAME.value);
            val location = (LinkedHashMap<String, Object>)record.get(Params.LOCATION.value);
            val lat = Double.parseDouble((String)location.get(Params.LAT.value));
            val lng = Double.parseDouble((String)location.get(Params.LNG.value));
            hotels.add(new Hotel(id, name, new Point(lng, lat)));
        }

        // hRepo.save
        hRepo.save(hotels);

        System.out.println("Saved:");
        hotels.stream().forEach(System.out::println);
    }
}
