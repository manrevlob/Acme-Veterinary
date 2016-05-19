package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BulletinService;
import controllers.AbstractController;
import domain.Bulletin;

@Controller
@RequestMapping("/bulletin/administrator")
public class bulletinAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private BulletinService bulletinService;

	// Constructors -----------------------------------------------------------

	public bulletinAdministratorController() {
		super();
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(int clinicId) {
		ModelAndView result;

		Bulletin bulletin = bulletinService.create(clinicId);

		result = new ModelAndView("bulletin/edit");
		result.addObject("bulletin", bulletin);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(int bulletinId) {
		ModelAndView result;

		Bulletin bulletin = bulletinService.findOne(bulletinId);

		result = new ModelAndView("bulletin/edit");
		result.addObject("bulletin", bulletin);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Bulletin bulletin, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("bulletin/edit");
		} else {
			try {
				bulletinService.save(bulletin);
				result = new ModelAndView(
						"redirect:/bulletin/listByClinic.do?clinicId="
								+ bulletin.getClinic().getId());
			} catch (Throwable oops) {
				result = new ModelAndView("bulletin/edit");
				result.addObject("bulletin", bulletin);
				result.addObject("message", "bulletin.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView cancel(int bulletinId) {
		ModelAndView result;
		Bulletin bulletin = bulletinService.findOne(bulletinId);

		try {

			bulletinService.delete(bulletin);
			result = new ModelAndView(
					"redirect:/bulletin/listByClinic.do?clinicId="
							+ bulletin.getClinic().getId());
		} catch (Throwable oops) {

			Collection<Bulletin> bulletins;
			int clinicId = bulletin.getClinic().getId();
			bulletins = bulletinService.findAllFromClinic(clinicId);

			result = new ModelAndView("bulletin/list");
			result.addObject("requestURI", "bulletin/listByClinic.do");
			result.addObject("bulletins", bulletins);
			result.addObject("clinicId", clinicId);
			result.addObject("message", "bulletin.delete.error");
		}

		return result;
	}
}
