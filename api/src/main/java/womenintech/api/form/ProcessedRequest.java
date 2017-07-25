package womenintech.api.form;

import lombok.Data;
import lombok.Value;

/**
 * @author fangda.wang
 */
@Value
public class ProcessedRequest {
    long userId;
    int count;
    String likedItinerary;
}
