package womenintech.api.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import womenintech.api.datamodel.Interest;

import java.util.List;

/**
 * @author fangda.wang
 */
public interface InterestRepository extends MongoRepository<Interest, String> {
    List<Interest> findAll();
    void deleteByUserId(int userId);

}
