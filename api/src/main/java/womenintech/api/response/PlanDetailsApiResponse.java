package womenintech.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import womenintech.api.datamodel.PlanDetails;

/**
 * @author fangda.wang
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Getter
@Setter
public class PlanDetailsApiResponse {

    public PlanDetails result;
    public String status;
}
