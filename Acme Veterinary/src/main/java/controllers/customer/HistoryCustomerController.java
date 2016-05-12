package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HistoryService;
import controllers.AbstractController;
import domain.History;

@Controller
@RequestMapping("/history/customer")
public class HistoryCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private HistoryService historyService;

	// Constructors -----------------------------------------------------------

	public HistoryCustomerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int petId) {
		ModelAndView result;

		Collection<History> histories;

		histories = historyService.findAllByPet(petId);

		result = new ModelAndView("history/list");
		result.addObject("requestURI", "history/customer/list.do");
		result.addObject("histories", histories);

		return result;
	}

	// Details --------------------------------------------------------------

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int historyId) {
		ModelAndView result;
		History history;

		history = historyService.findOne(historyId);

		result = new ModelAndView("history/details");
		result.addObject("requestURI", "history/customer/details.do");
		result.addObject("history", history);
		
		return result;

	}

}
