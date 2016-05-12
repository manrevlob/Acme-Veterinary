package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.PetService;
import controllers.AbstractController;
import domain.Pet;

@Controller
@RequestMapping("/pet/customer")
public class PetCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PetService petService;

	// Constructors -----------------------------------------------------------

	public PetCustomerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<Pet> pets;

		pets = petService.findAllOwner();

		result = new ModelAndView("pet/list");
		result.addObject("requestURI", "pet/customer/list.do");
		result.addObject("pets", pets);

		return result;
	}

}
