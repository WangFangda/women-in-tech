package womenintech.api.logic;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import womenintech.api.config.BookingApiConfiguration;
import womenintech.api.datamodel.AutocompleteUnit;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author fangda.wang
 */
@Service
public class AutoCompleteService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpHeaders httpHeaders;
    @Autowired
    private BookingApiConfiguration apiConfiguration;


    private static final String TEXT = "TEXT";
    public static final String city_ufi = "city_ufi";
    public static final String dest_id = "dest_id";
    public static final String rtl= "rtl";
    public static final String hotels= "hotels";
    public static final String dest_type= "dest_type";
    public static final String name= "name";
    public static final String city_name= "city_name";
    public static final String label= "label";
    public static final String url= "url";
    public static final String longitude= "longitude";
    public static final String latitude= "latitude";
    public static final String lc= "lc";
    public static final String region= "region";
    public static final String country= "country";
    public static final String nr_hotels= "nr_hotels";
    public static final String cc1= "cc1";
    public static final String type= "type";


    public List<AutocompleteUnit> exec(String text) {

        val request = new HttpEntity<String>(httpHeaders);
        val districtHotelUrl = apiConfiguration.autocompleteUrl.replaceAll(TEXT, text);
        val body = (List<LinkedHashMap<String, Object>>) restTemplate.exchange(districtHotelUrl, HttpMethod.GET, request, List.class).getBody();


        List<AutocompleteUnit> result = new ArrayList<>();
        for (LinkedHashMap<String, Object> props: body) {
            AutocompleteUnit unit = new AutocompleteUnit();
            unit.setCity_name((String)props.get(city_ufi));
            unit.setDest_id((String)props.get(dest_id));
            unit.setRtl((Integer)props.get(rtl));
            unit.setHotels((String)props.get(hotels));
            unit.setDest_type((String)props.get(dest_type));
            unit.setName((String)props.get(name));
            unit.setCity_name((String)props.get(city_name));
            unit.setLabel((String)props.get(label));
            unit.setUrl((String)props.get(url));
            unit.setLatitude((String)props.get(latitude));
            unit.setLongitude((String)props.get(longitude));
            unit.setLc((String)props.get(lc));
            unit.setRegion((String)props.get(region));
            unit.setCountry((String)props.get(country));
            unit.setNr_hotels((String)props.get(nr_hotels));
            unit.setCc1((String)props.get(cc1));
            unit.setType((String)props.get(type));
            result.add(unit);
        }

        return result;

    }
}
