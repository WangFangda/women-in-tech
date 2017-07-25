package womenintech.api.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author fangda.wang
 */
@Data
@Getter
@Setter
public class InputForm {
    private long userId;
    private String checkIn;
    private String checkOut;
    private String likedItinerary;
}
