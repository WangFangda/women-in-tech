package womenintech.api.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author fangda.wang
 */
@Data
@AllArgsConstructor
@Document(collection = "hotels")
public class Hotel {
    @Id
    private long id;
    private String name;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2D)
    private Point location;
}
