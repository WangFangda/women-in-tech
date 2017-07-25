package womenintech.api.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author fangda.wang
 */
@Data
@AllArgsConstructor
public class Route {
    private List<Place> places;
}
