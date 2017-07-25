package womenintech.api.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import womenintech.api.datamodel.Itinerary;

import java.util.List;

/**
 * @author fangda.wang
 */
public interface ItineraryRepository extends MongoRepository<Itinerary, String> {

	List<Itinerary> findByUserIdBetween(long start, long end);
	List<Itinerary> findByCountEquals(long value);
	List<Itinerary> findByCountBetween(int least, int most);


}
