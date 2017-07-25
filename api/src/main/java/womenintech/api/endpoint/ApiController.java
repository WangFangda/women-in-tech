package womenintech.api.endpoint;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import womenintech.api.datamodel.Itinerary;
import womenintech.api.form.InputForm;
import womenintech.api.logic.ItineraryPickerService;
import womenintech.api.repo.ItineraryRepository;
import womenintech.api.util.InputFormConversionUtil;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author fangda.wang
 */
@RestController
public class ApiController {

	@Autowired
	private ItineraryPickerService iPicker;

	@GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Itinerary> search(@Valid InputForm input) {

		val request = InputFormConversionUtil.toProcessedRequest(input);
		return iPicker.pick(request);
	}

//	@GetMapping(path = "/searchIdBetween", produces = MediaType.APPLICATION_JSON_VALUE)
//	public List<Itinerary> searchIdBetween(
//			@RequestParam("start") long start,
//			@RequestParam("end") long end
//	) {
//		return repo.findByUserIdBetween(start, end);
//	}
}
