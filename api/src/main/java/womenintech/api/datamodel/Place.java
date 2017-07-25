package womenintech.api.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.geo.Point;

/**
 * @author fangda.wang
 */
@Data
@AllArgsConstructor
public class Place {
    private String name;
    private Point location;
}
