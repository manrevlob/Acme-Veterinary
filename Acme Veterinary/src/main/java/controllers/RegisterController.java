package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ClinicService;
import services.CustomerService;
import services.VeterinaryService;
import domain.Clinic;
import forms.CustomerForm;
import forms.VeterinaryForm;

@Controller
@RequestMapping("/register")
public class RegisterController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CustomerService customerService;
	@Autowired
	private VeterinaryService veterinaryService;
	@Autowired
	private ClinicService clinicService;

	// Creation Customer -----------------------------------------------------

	@RequestMapping(value = "/createCustomer", method = RequestMethod.GET)
	public ModelAndView createCustomer() {
		ModelAndView result;
		CustomerForm customerForm = new CustomerForm();
		result = new ModelAndView("register/registerAsCustomer");
		result.addObject("customerForm", customerForm);
		return result;
	}

	// Creation Veterinary -----------------------------------------------------

	@RequestMapping(value = "/createVeterinary", method = RequestMethod.GET)
	public ModelAndView createVeterinary() {
		ModelAndView result;
		Collection<Clinic> clinics;

		VeterinaryForm veterinaryForm = new VeterinaryForm();
		clinics = clinicService.findAllNotDeleted();

		result = new ModelAndView("register/registerAsVeterinary");
		result.addObject("veterinaryForm", veterinaryForm);
		result.addObject("clinics", clinics);

		return result;
	}

	// Edition Customer -----------------------------------------------------

	@RequestMapping(value = "/createCustomer", method = RequestMethod.POST, params = "save")
	public ModelAndView saveC(@Valid CustomerForm customerForm,
			BindingResult binding) {
		ModelAndView result = null;
		Boolean contraseña;

		contraseña = customerForm.getSecondPassword().equals(
				customerForm.getPassword());

		if (binding.hasErrors() || !contraseña) {
			result = new ModelAndView("register/registerAsCustomer");
			result.addObject("customerForm", customerForm);
			if (!contraseña) {
				result.addObject("message", "register.commit.password");
			}
		} else {
			try {
				customerService.save(customerForm);
				result = new ModelAndView("redirect:../security/login.do");
			} catch (Throwable oops) {
				result = new ModelAndView("register/registerAsCustomer");
				result.addObject("customerForm", customerForm);
				result.addObject("message", "register.commit.error");
			}
		}
		return result;
	}

	// Edition Veterinary ---------------------------------------------------

	@RequestMapping(value = "/createVeterinary", method = RequestMethod.POST, params = "save")
	public ModelAndView saveV(@Valid VeterinaryForm veterinaryForm,
			BindingResult binding) {
		ModelAndView result = null;
		Boolean contraseña;
		Collection<Clinic> clinics;
		clinics = clinicService.findAllNotDeleted();

		contraseña = veterinaryForm.getSecondPassword().equals(
				veterinaryForm.getPassword());

		if (binding.hasErrors() || !contraseña) {
			result = new ModelAndView("register/registerAsVeterinary");
			result.addObject("veterinaryForm", veterinaryForm);
			result.addObject("clinics", clinics);
			if (!contraseña) {
				result.addObject("message", "register.commit.password");
			}
		} else {
			try {
				veterinaryService.save(veterinaryForm);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = new ModelAndView("register/registerAsVeterinary");
				result.addObject("veterinaryForm", veterinaryForm);
				result.addObject("clinics", clinics);
				result.addObject("message", "register.commit.error");
			}
		}
		return result;
	}

}
