package womenintech.api.response;

import jdk.nashorn.internal.runtime.events.RecompilationEvent;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import womenintech.api.datamodel.Preference;
import womenintech.api.datamodel.Recommendation;

import java.util.List;

/**
 * @author fangda.wang
 */
@Data
@Getter
@Setter
public class FrontEndResponse {
    private List<Preference> preferences;
    private List<Recommendation> recommendations;
}
