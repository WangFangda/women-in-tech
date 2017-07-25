package womenintech.api.logic;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import womenintech.api.datamodel.Interest;
import womenintech.api.datamodel.Preference;
import womenintech.api.datamodel.Recommendation;
import womenintech.api.repo.InterestRepository;

import java.util.*;

/**
 * @author fangda.wang
 */
@Service
public class RecommendationService {

    private static final int MAX = 6;
    private static final int ROUND = 2;

    @Autowired
    private InterestRepository iRepo;

    private boolean contains(List<Preference> list, String target) {
        for (int i = 0; i < list.size(); i++) {
            if (target.equals(list.get(i).getName()))
                return true;
        }
        return false;
    }

    public List<Recommendation> exec(List<Preference> preferences, int userId) {
        val interests = iRepo.findAll();
        val recommendations = getRecomendations(userId, interests);
        if (null == recommendations) return new ArrayList<>();
        if (recommendations.size() <= MAX) return recommendations;

        List<Recommendation> results = new ArrayList<>();
        Set<Integer> distincts = new HashSet<>();
        val random = new Random();

        int cnt = 0;
        while(distincts.size() != MAX && cnt < ROUND * MAX) {
            cnt++;
            val num = random.nextInt(recommendations.size());
            if (contains(preferences, recommendations.get(num).name))
                continue;
            distincts.add(num);
        }

        List<Integer> list = new ArrayList<>(distincts);

        for (int i = 0; i < distincts.size(); i++)
            results.add(recommendations.get(list.get(i)));

        return results;
    }

    // this function was originally made for
    // Destination
    //   int: userId
    //   List<String>: names

    // output: List<String>


    // Interest
    // int: userId
    // List<Recommendation> relevantDestinations

    // Recommendation
    //  String: name
    //  String: url

    // List<Recommendation>

    private static int longestCommonSubseq(int[] a, int[] b) {
        int m = a.length;
        int n = b.length;
        int[][] dp = new int[m+1][n+1];

        for(int i=0; i<=m; i++){
            for(int j=0; j<=n; j++){
                if(i==0 || j==0){
                    dp[i][j]=0;
                }else if(a[i-1]==b[j-1]){
                    dp[i][j] = 1 + dp[i-1][j-1];
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        return dp[m][n];
    }

    List<Recommendation> getRecomendations(int id, List<Interest> destinations) {
        int user_ix = 1;
        HashMap<Integer, Recommendation> hmap = new HashMap<Integer, Recommendation>();
        int hashes[][] = new int[destinations.size()][];

        for(int i = 0; i < destinations.size(); ++i) {
            hashes[i] = new int[destinations.get(i).relevantDestinations.size()];
            if (destinations.get(i).userId == id) user_ix = i;
            for (int j = 0; j < destinations.get(i).relevantDestinations.size(); ++j) {
                hashes[i][j] = destinations.get(i).relevantDestinations.get(j).hashCode();
                hmap.put(hashes[i][j], destinations.get(i).relevantDestinations.get(j));
            }
            Arrays.sort(hashes[i]);
        }

        int maxlength=0, cextra, extra=0, closest_user_ix=user_ix;
        for (int i = 0; i < hashes.length; ++i) {
            if (i == user_ix) continue;
            int cmaxlength = longestCommonSubseq(hashes[i], hashes[user_ix]);

            if (cmaxlength > maxlength) {
                cextra = hashes[i].length - cmaxlength;
                if (cextra > 0) {
                    closest_user_ix = i;
                    extra = cextra;
                    maxlength = cmaxlength;
                }
            } else if (maxlength > 0 && cmaxlength == maxlength) {
                cextra = hashes[i].length - cmaxlength;
                if (cextra > extra) {
                    closest_user_ix = i;
                    extra = cextra;
                    maxlength = cmaxlength;
                }
            }
        }

        if (closest_user_ix == user_ix) return null;

        List<Recommendation> recoms =  new ArrayList<Recommendation>();
        for (int i=0; i<hashes[closest_user_ix].length; ++i) {
            if (Arrays.binarySearch(hashes[user_ix], hashes[closest_user_ix][i]) < 0) {
                recoms.add(hmap.get(hashes[closest_user_ix][i]));
            }
        }
        return recoms;
    }


}
