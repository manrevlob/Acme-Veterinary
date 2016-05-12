package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SpamWordService;
import controllers.AbstractController;
import domain.SpamWord;

@Controller
@RequestMapping("/spamword/administrator")
public class SpamWordAdministratorController extends AbstractController {
	// Services ---------------------------------------------------------------

	@Autowired
	private SpamWordService spamWordService;

	// Constructors -----------------------------------------------------------

	public SpamWordAdministratorController() {
		super();
	}

	// List -------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		Collection<SpamWord> spamWords = spamWordService.findAll();

		ModelAndView result;
		result = new ModelAndView("spamword/list");
		result.addObject("requestURI", "spamword/administrator/list.do");
		result.addObject("spamWords", spamWords);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SpamWord spamWord = spamWordService.create();

		result = new ModelAndView("spamword/create");
		result.addObject("spamWord", spamWord);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@Valid int spamWordId) {
		ModelAndView result;
		SpamWord spamWord = spamWordService.findOne(spamWordId);

		result = new ModelAndView("spamword/edit");
		result.addObject("spamWord", spamWord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid SpamWord spamWord, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("spamword/edit");
		} else {

			try {

				spamWordService.save(spamWord);
				result = new ModelAndView(
						"redirect:/spamword/administrator/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView("spamword/edit");
				result.addObject("spamWord", spamWord);
				result.addObject("message", "spamWord.commit.error");

			}
		}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid SpamWord spamWord, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("spamword/edit");

		} else {
			try {
				spamWordService.delete(spamWord);
				result = new ModelAndView(
						"redirect:/spamword/administrator/list.do");

			} catch (Throwable oops) {
				result = new ModelAndView("spamword/edit");
				result.addObject("message", "spamword.delete.serviceError");
			}
		}
		return result;
	}
}
