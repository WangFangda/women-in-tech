package womenintech.api.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import womenintech.api.datamodel.Itinerary;
import womenintech.api.form.ProcessedRequest;
import womenintech.api.repo.ItineraryRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fangda.wang
 */
@Service
public class ItineraryPickerService {

    private static final int DISPLAED_ITINERARIES = 5;

    @Autowired
    private UserProfilerService uProfiler;

    @Autowired
    private ItineraryRepository iRepo;

    public List<Itinerary> pick(ProcessedRequest request) {

        List<Itinerary> all = iRepo.findByCountBetween(1, request.getCount());
        List<Itinerary> displayed = new ArrayList<>();

        int maxCount = 0;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getCount() > maxCount)
                maxCount = all.get(i).getCount();
        }

        if (maxCount == request.getCount()) {
            for (int i = 0; i < all.size(); i++) {
                if (maxCount == all.get(i).getCount())
                    displayed.add(all.get(i));
            }
        } else {
			/* pick up the first 5 itineraries */
            int minCnt = Integer.MAX_VALUE;
            int minPos = 0;
            for (int i = 0; i < DISPLAED_ITINERARIES; i++) {
                displayed.add(all.get(i));
                if (minCnt > all.get(i).getCount()) {
                    minCnt = all.get(i).getCount();
                    minPos = displayed.size()-1;
                }
            }
			/* displayed.size() == 5 */
            for (int i = DISPLAED_ITINERARIES; i < all.size(); i++) {
                if ((all.get(i).getCount() > minCnt) ||
                        ((all.get(i).getCount() == minCnt) &&
                                uProfiler.isSecondBetter(request.getUserId(), displayed.get(minPos).getUserId(), all.get(i).getUserId()))) {
                    displayed.remove(minPos);
                    displayed.add(all.get(i));
                    minCnt = all.get(i).getCount();
                    minPos = DISPLAED_ITINERARIES - 1;
                    for (int y = 0; y < DISPLAED_ITINERARIES - 1; y++) {
                        if (minCnt > displayed.get(y).getCount()) {
                            minCnt = displayed.get(y).getCount();
                            minPos = y;
                        }
                    }
                }
            }
        }

        return displayed;

    }
}
