package womenintech.api.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author fangda.wang
 */
@Data
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "interests")
public class Interest {
    @Id
    public int userId;
    @Indexed
    public List<Recommendation> relevantDestinations;
}
