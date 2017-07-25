package womenintech.api.datamodel;

/**
 * @author fangda.wang
 */

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class GooglePlace {
    private String formatted_address;
    private String place_id;
}
