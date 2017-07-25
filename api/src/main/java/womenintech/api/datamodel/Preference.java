package womenintech.api.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author fangda.wang
 */
@Data
@Getter
@Setter
@AllArgsConstructor
public class Preference {
    private String name;
    private String action;
    private String imgUrl;
}
