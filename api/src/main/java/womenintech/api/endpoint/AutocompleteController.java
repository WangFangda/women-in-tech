package womenintech.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import womenintech.api.datamodel.AutocompleteUnit;
import womenintech.api.logic.AutoCompleteService;

import java.util.List;

/**
 * @author fangda.wang
 */
@RestController
public class AutocompleteController {

    @Autowired
    private AutoCompleteService aService;

    @GetMapping(path = "/autocomplete", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AutocompleteUnit> exec(@RequestParam("text") String text) {
        return aService.exec(text);
    }
}
