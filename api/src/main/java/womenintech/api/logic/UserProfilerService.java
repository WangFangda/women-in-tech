package womenintech.api.logic;

import org.springframework.stereotype.Service;

/**
 * @author fangda.wang
 */
@Service
public class UserProfilerService {

    /**
     * Fake user profiling.
     *
     * @param requestUser
     * @param firstUser
     * @param secondUser
     * @return
     */
    public boolean isFirstBetter(long requestUser, long firstUser, long secondUser) {
        return Math.abs(firstUser-requestUser) < Math.abs(secondUser-requestUser);
    }

    public boolean isSecondBetter(long requestUser, long firstUser, long secondUser) {
        return !isFirstBetter(requestUser, firstUser, secondUser);
    }
}
