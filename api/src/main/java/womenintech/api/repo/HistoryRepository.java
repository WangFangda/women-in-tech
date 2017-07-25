package womenintech.api.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import womenintech.api.datamodel.History;
import womenintech.api.datamodel.Preference;
import womenintech.api.response.FrontEndResponse;

/**
 * @author fangda.wang
 */
public interface HistoryRepository extends MongoRepository<History, Integer> {
    void delete(History history);
    History findByUserId(Integer userId);
}
