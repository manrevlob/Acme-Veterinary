package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BulletinService;
import domain.Bulletin;

@Controller
@RequestMapping("/bulletin")
public class bulletinController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private BulletinService bulletinService;

	// Constructors -----------------------------------------------------------

	public bulletinController() {
		super();
	}

	// ListAll ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Object[]> bulletins;

		bulletins = bulletinService.findAll();

		result = new ModelAndView("bulletin/list");
		result.addObject("requestURI", "bulletin/list.do");
		result.addObject("bulletins", bulletins);

		return result;

	}

	// ListByClinic -----------------------------------------------------------

	@RequestMapping(value = "/listByClinic", method = RequestMethod.GET)
	public ModelAndView listByClinic(int clinicId) {
		ModelAndView result;
		Collection<Bulletin> bulletins;

		bulletins = bulletinService.findAllFromClinic(clinicId);

		result = new ModelAndView("bulletin/list");
		result.addObject("requestURI", "bulletin/listByClinic.do");
		result.addObject("bulletins", bulletins);

		return result;

	}

}
