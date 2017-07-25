package womenintech.api.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author fangda.wang
 */
@Data
@Setter
@Getter
@AllArgsConstructor
public class Recommendation {
    public String name;
    public String url;
}
