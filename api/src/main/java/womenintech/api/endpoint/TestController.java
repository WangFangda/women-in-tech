package womenintech.api.endpoint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author fangda.wang
 */
@Controller
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
