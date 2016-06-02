package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.PetTypeService;
import controllers.AbstractController;
import domain.PetType;

@Controller
@RequestMapping("/petType/administrator")
public class PetTypeAdminitratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PetTypeService petTypeService;

	// Constructors -----------------------------------------------------------

	public PetTypeAdminitratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<PetType> petTypes;

		petTypes = petTypeService.findAll();

		result = new ModelAndView("petType/list");
		result.addObject("requestURI", "petType/administrator/list.do");
		result.addObject("petTypes", petTypes);

		return result;
	}

	// Create ------------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		PetType petType;
		petType = petTypeService.create();

		result = new ModelAndView("petType/create");
		result.addObject("petType", petType);
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@Valid int petTypeId) {
		ModelAndView result;
		PetType petType;

		petType = petTypeService.findOne(petTypeId);
		result = new ModelAndView("petType/edit");
		result.addObject("petType", petType);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid PetType petType, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("petType/edit");
		} else {
			try {
				if (petTypeService.checkName(petType)){
					petType = petTypeService.save(petType);
					result = new ModelAndView(
							"redirect:/petType/administrator/list.do");
				}else{
					result = new ModelAndView("petType/edit");
					result.addObject("petType", petType);
					result.addObject("message", "petType.commit.errorName");
				}
			} catch (Throwable oops) {
				result = new ModelAndView("petType/edit");
				result.addObject("petType", petType);
				result.addObject("message", "petType.commit.error");
			}
		}
		return result;
	}

}
