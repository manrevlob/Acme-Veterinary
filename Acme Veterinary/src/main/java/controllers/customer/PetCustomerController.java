package controllers.customer;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.PetService;
import services.PetTypeService;
import controllers.AbstractController;
import domain.Pet;
import domain.PetType;

@Controller
@RequestMapping("/pet/customer")
public class PetCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PetService petService;
	@Autowired
	private PetTypeService petTypeService;

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

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		Collection<PetType> petTypes;
		Pet pet;

		petTypes = petTypeService.findAll();
		pet = petService.create();

		result = new ModelAndView("pet/create");
		result.addObject("pet", pet);
		result.addObject("petTypes", petTypes);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(int petId) {
		ModelAndView result;

		Collection<PetType> petTypes;
		Pet pet;
		if (petService.isOwner(petId)) {
			petTypes = petTypeService.findAll();
			pet = petService.findOne(petId);
			result = new ModelAndView("pet/edit");
			result.addObject("pet", pet);
			result.addObject("petTypes", petTypes);
		} else {
			result = new ModelAndView("misc/403");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Pet pet, BindingResult binding) {
		ModelAndView result;
		Collection<PetType> petTypes = petTypeService.findAll();
		if (binding.hasErrors()) {
			result = new ModelAndView("pet/edit");
			result.addObject("pet", pet);
			result.addObject("petTypes", petTypes);
		} else {
			try {
				petService.save(pet);
				result = new ModelAndView("redirect:/pet/customer/list.do");
			} catch (Throwable oops) {

				result = new ModelAndView("pet/edit");
				result.addObject("pet", pet);
				result.addObject("petTypes", petTypes);
				result.addObject("message", "pet.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView cancel(int petId) {
		ModelAndView result;

		try {
			if (petService.isOwner(petId)) {
				petService.delete(petId);
				result = new ModelAndView("redirect:/pet/customer/list.do");
			} else {
				result = new ModelAndView("misc/403");
			}

		} catch (Throwable oops) {
			Collection<Pet> pets;

			pets = petService.findAllOwner();

			result = new ModelAndView("pet/list");
			result.addObject("requestURI", "pet/customer/list.do");
			result.addObject("pets", pets);
			result.addObject("message", "pet.delete.error");
		}

		return result;
	}

	// Details --------------------------------------------------------------

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(int petId, HttpServletRequest httpServletRequest) {
		ModelAndView result;
		Pet pet = petService.findOne(petId);

		result = new ModelAndView("pet/details");
		result.addObject("pet", pet);
		return result;
	}


	
}
