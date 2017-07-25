package womenintech.api.logic;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import womenintech.api.config.GoogleApiConfiguration;
import womenintech.api.datamodel.GooglePlace;
import womenintech.api.datamodel.PlanDetails;
import womenintech.api.datamodel.Recommendation;
import womenintech.api.response.GeocodeApiResponse;
import womenintech.api.response.PlanDetailsApiResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fangda.wang
 */
@Service
public class GoogleApiService {

    @AllArgsConstructor
    private enum Params {
        KEY_LOWER("key"),
        ADDRESS("address"),
        PLACE_ID("placeid"),

        KEY_UPPER("KEY"),
        PHOTO("PHOTO");

        public final String value;
    }


    @Autowired
    private GoogleApiConfiguration configuration;

    @Autowired
    private RestTemplate restTemplate;

    public GooglePlace getInformation(String address) {

        System.out.println(address);
        val city = getPlaceIdAndName(address);
        return new GooglePlace(city.getFormatted_address(), city.getPlace_id());
    }

    public String getUrlFromAddress(String address) {
        System.out.println(address);
        val city = getPlaceIdAndName(address);
        return getUrl(city.getPlace_id());
    }

    public String getUrl(String place_id) {
        val photo_reference = getPlaceReference(place_id);
        if ("".equals(photo_reference)) return "";
        return makeUrl(photo_reference);
    }

    private GooglePlace getPlaceIdAndName(String address) {

        Map<String, String> params = new HashMap<>();
        params.put(Params.KEY_LOWER.value, configuration.key);
        params.put(Params.ADDRESS.value, address);

        return restTemplate.getForObject(configuration.geocode, GeocodeApiResponse.class, params).getResults().get(0);
    }

    private String getPlaceReference(String placeId) {
        Map<String, String> params = new HashMap<>();
        params.put(Params.KEY_LOWER.value, configuration.key);
        params.put(Params.PLACE_ID.value, placeId);

        PlanDetails result = restTemplate
                .getForObject(configuration.placeDetails, PlanDetailsApiResponse.class, params)
                .getResult();
        if (null != result.getPhotos()) {
            return result.getPhotos().get(0).getPhoto_reference();
        }

        return "";
    }

    private String makeUrl(String photoReference) {

        return configuration.placePhoto
                .replaceAll(Params.KEY_UPPER.value, configuration.key)
                .replaceAll(Params.PHOTO.value, photoReference);
    }
}

