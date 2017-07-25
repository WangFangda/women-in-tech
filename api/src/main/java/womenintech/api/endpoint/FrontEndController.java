package womenintech.api.endpoint;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;
import womenintech.api.datamodel.History;
import womenintech.api.datamodel.Preference;
import womenintech.api.logic.GoogleApiService;
import womenintech.api.logic.RecommendationService;
import womenintech.api.repo.HistoryRepository;
import womenintech.api.response.FrontEndResponse;

import java.util.ArrayList;

/**
 * @author fangda.wang
 */
@RestController
public class FrontEndController {

    private static final int FIXED_USER = 100;
    private static final String ON = "ON";
    private static final String OFF = "OFF";
    private static final String RELOAD = "RELOAD";

    @Autowired
    private HistoryRepository hRepo;

    @Autowired
    private GoogleApiService gService;

    @Autowired
    private RecommendationService recommendation;

    @GetMapping("/destinations")
    public FrontEndResponse frontEnd(@RequestParam("action") String action, @RequestParam("name") String city) {

        System.out.println("/destinations"+"\taction="+action+"\tname"+city);

        History history = hRepo.findByUserId(FIXED_USER);

        if (!RELOAD.equals(action) && !RELOAD.toLowerCase().equals(action)) {

            if (null != history) {
                hRepo.delete(history);
            } else {
                history = new History(FIXED_USER, new ArrayList<>());
            }

            if (ON.equals(action) || ON.toLowerCase().equals(action)) {

                boolean found = false;
                for (int i = 0; i < history.getPreferences().size(); i++) {
                    if (StringUtils.equals(history.getPreferences().get(i).getName(), city)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    val img = gService.getUrlFromAddress(city);
                    val newPreference = new Preference(city, ON, img);
                    history.getPreferences().add(newPreference);
                }
            } else if (OFF.equals(action) || OFF.toLowerCase().equals(action)){
                for (int i = 0; i < history.getPreferences().size(); i++) {
                    if (StringUtils.equals(history.getPreferences().get(i).getName(), city))
                        history.getPreferences().remove(i);
                }
            }

            hRepo.save(history);
        }

        FrontEndResponse response = new FrontEndResponse();
        response.setPreferences(history.getPreferences());
        response.setRecommendations(recommendation.exec(history.getPreferences(), FIXED_USER));

        return response;
    }
}
