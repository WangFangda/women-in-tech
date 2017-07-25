package womenintech.api.datamodel;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author fangda.wang
 */
@Data
@Getter
@Setter
public class PlanDetails {
    List<Photo> photos;
}
