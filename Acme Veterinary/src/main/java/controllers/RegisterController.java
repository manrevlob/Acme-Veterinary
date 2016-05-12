package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import forms.CustomerForm;

@Controller
@RequestMapping("/register")
public class RegisterController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CustomerService customerService;

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/createCustomer", method = RequestMethod.GET)
	public ModelAndView createUser() {
		ModelAndView result;
		CustomerForm customerForm = new CustomerForm();
		result = new ModelAndView("register/registerAsCustomer");
		result.addObject("customerForm", customerForm);
		return result;
	}

	// Edition ----------------------------------------------------------------

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

	// Ancillary methods ------------------------------------------------------

}
