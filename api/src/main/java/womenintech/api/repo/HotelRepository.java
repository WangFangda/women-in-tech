package womenintech.api.repo;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import womenintech.api.datamodel.Hotel;
import womenintech.api.datamodel.Itinerary;

import java.util.List;

/**
 * @author fangda.wang
 */
public interface HotelRepository extends MongoRepository<Hotel, String> {
    List<Hotel> findByLocationNear(Point center, Distance radius);
}
