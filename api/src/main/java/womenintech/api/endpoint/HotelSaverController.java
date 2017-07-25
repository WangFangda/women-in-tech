package womenintech.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import womenintech.api.logic.HotelSaverService;

/**
 * @author fangda.wang
 */
@RestController
public class HotelSaverController {

    private static final String city = "-246227"; // tokyo

    @Autowired
    private HotelSaverService hSaver;

    @GetMapping("/initHotels")
    public String initHotels(@RequestParam("districts") String districts) {

        hSaver.save(districts, city);

        return "Done";
    }
}
