package womenintech.api.util;

import org.springframework.util.StringUtils;
import womenintech.api.form.InputForm;
import womenintech.api.form.ProcessedRequest;

import java.util.Arrays;
import java.util.Random;

/**
 * @author fangda.wang
 */
public final class InputFormConversionUtil {

    private static final String HYPHEN = "-";

    /* non leap year */
    private static final int[] DAYS_IN_MONTH = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private static final int DEFAULT_TRAVEL_DAYS = 5;
    private static final int TOTAL_ITINERARIS = 45;

    private static Random random = new Random();
    public static ProcessedRequest toProcessedRequest(InputForm form) {

        int count = DEFAULT_TRAVEL_DAYS;
        if (!StringUtils.isEmpty(form.getCheckIn()) && !StringUtils.isEmpty(form.getCheckOut())) {
            Integer[] checkIn = Arrays.stream(form.getCheckIn().split(HYPHEN)).map(Integer::parseInt).toArray(Integer[]::new);
            Integer[] checkOut = Arrays.stream(form.getCheckOut().split(HYPHEN)).map(Integer::parseInt).toArray(Integer[]::new);
            count = (DAYS_IN_MONTH[checkIn[1]]-checkIn[2]+1) + checkOut[2];
            if (checkIn[0] % 4 == 0 && checkIn[1] == 2) // Feb in a leap year
                count++;
        }

        long userId = form.getUserId();
        if (userId == 0L) {
            userId = random.nextInt(TOTAL_ITINERARIS) + 1;
        } else {
            userId = (userId % TOTAL_ITINERARIS) + 1;
        }

        return new ProcessedRequest(userId, count, form.getLikedItinerary());
    }
}
