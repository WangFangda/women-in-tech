package womenintech.api.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import womenintech.api.datamodel.GooglePlace;

import java.util.List;

/**
 * @author fangda.wang
 */
@Data
@Getter
@Setter
public class GeocodeApiResponse {
    public List<GooglePlace> results;
    public String status;
}
