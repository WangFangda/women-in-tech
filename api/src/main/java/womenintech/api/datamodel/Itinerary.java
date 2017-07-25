package womenintech.api.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author fangda.wang
 */
@Data
@AllArgsConstructor
@Document(collection = "itineraries")
public class Itinerary {

    @Data
    @AllArgsConstructor
    private static class Hotel {
        private String name;
        private Point location;
    }

    @Id
    private String id;
    private long userId;
    @Indexed
    private int count;
    private List<Route> routes;
    private List<Itinerary.Hotel> hotels;
}

