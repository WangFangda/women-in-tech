package womenintech.api.endpoint;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import womenintech.api.datamodel.GooglePlace;
import womenintech.api.datamodel.Interest;
import womenintech.api.datamodel.Recommendation;
import womenintech.api.logic.GoogleApiService;
import womenintech.api.repo.InterestRepository;

import java.io.*;
import java.util.*;

/**
 * @author fangda.wang
 */
@RestController
public class InterestsSaverController {

    private static final String FILE =  "C:\\Users\\fangda.wang\\women-in-tech\\clawer\\plans.txt";

    @Autowired
    private GoogleApiService gCaller;

    @Autowired
    private InterestRepository iRepo;

    @GetMapping("/deleteInterests")
    public String deleteInterests(@RequestParam("name") String name) {

        List<Interest> all = iRepo.findAll();

        for (int user = 0; user < all.size(); user++) {
            for (int i = 0; i < all.get(user).relevantDestinations.size(); i++) {
                if (name.equals(all.get(user).relevantDestinations.get(i).name)) {
                    iRepo.deleteByUserId(user);
                    all.get(user).relevantDestinations.remove(i);
                    iRepo.save(all.get(user));
                }
            }
        }

        return "Done";
    }

    @GetMapping("/saveInterests")
    public String exec() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE));

        Map<String, Set<String>> userMap = new HashMap<>();
        Set<GooglePlace> citySet = new HashSet<>();

        String line = reader.readLine();
        while (null != line) {
            String[] comps = line.split(",");
            val userId = comps[0].trim();
            val rawString = comps[1].replaceAll("<p class=\"xc_city\">", "").replaceAll("</p>", "").trim().split(" · ");

            for (int i = 1; i < rawString.length-1; i++) {
                val destination = rawString[i];
                val googlePlace = gCaller.getInformation(destination);

                if (userMap.containsKey(userId)) {
                    Set<String> set = userMap.get(userId);
                    set.add(googlePlace.getFormatted_address());
                    userMap.put(userId, set);
                } else {
                    Set<String> set = new HashSet<>();
                    set.add(googlePlace.getFormatted_address());
                    userMap.put(userId, set);
                }

                citySet.add(googlePlace);
            }
            line = reader.readLine();
        }

        Map<String, String> urlMaps = new HashMap<>();
        for (GooglePlace googlePlace: citySet) {
            urlMaps.put(googlePlace.getFormatted_address(), gCaller.getUrl(googlePlace.getPlace_id()));
        }

        List<Interest> interests = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry: userMap.entrySet()) {
            val userId = Integer.valueOf(entry.getKey());
            val cities = entry.getValue();

            List<Recommendation> relevantDestinations = new ArrayList<>();
            for (String destination: cities) {
                relevantDestinations.add(new Recommendation(destination, urlMaps.get(destination)));
            }

            interests.add(new Interest(userId, relevantDestinations));
        }

        iRepo.save(interests);

        return "Done";
    }
}
