package controllers.actor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CustomerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Customer;
import forms.PassForm;

@Controller
@RequestMapping("/profile/actor")
public class ProfileActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService actorService;
	@Autowired
	private CustomerService customerService;

	// Constructors -----------------------------------------------------------

	public ProfileActorController() {
		super();
	}

	// List -------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		Actor actor;
		actor = actorService.findByPrincipal();

		ModelAndView result;
		result = new ModelAndView("profile/list");
		result.addObject("requestURI", "profile/list.do");
		result.addObject("actor", actor);
		if (actorService.isCustomer()) {
			Customer customer;
			customer = customerService.findByPrincipal();
			result.addObject("customer", customer);
		}

		return result;
	}

	// Edit profile

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Actor actor;
		actor = actorService.findByPrincipal();

		result = new ModelAndView("profile/edit");
		result.addObject("actor", actor);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Actor actor, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("profile/edit");
		} else {
			try {
				actorService.changeProfile(actor);
				result = new ModelAndView("redirect:/profile/actor/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView("profile/edit");
				result.addObject("actor", actor);
				result.addObject("message", "actor.commit.error");
			}
		}
		return result;
	}

	// Edit profile

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public ModelAndView changePass(@Valid int actorId) {
		ModelAndView result;
		PassForm passForm;

		passForm = new PassForm();

		result = new ModelAndView("profile/changePassword");
		result.addObject("passForm", passForm);

		return result;
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, params = "save")
	public ModelAndView changePass(PassForm passForm, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("profile/changePassword");
		} else {
			try {
				actorService.changePassword(passForm);
				result = new ModelAndView("redirect:/profile/actor/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView("profile/changePassword");
				result.addObject("passForm", passForm);
				result.addObject("message", "actor.commit.errorPass");
			}
		}
		return result;
	}

}
