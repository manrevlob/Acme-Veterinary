package controllers.customer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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

		petTypes = petTypeService.findAll();
		pet = petService.findOne(petId);

		result = new ModelAndView("pet/edit");
		result.addObject("pet", pet);
		result.addObject("petTypes", petTypes);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Pet pet, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("pet/edit");
		} else {
			try {
				petService.save(pet);
				result = new ModelAndView("redirect:/pet/customer/list.do");
			} catch (Throwable oops) {

				Collection<PetType> petTypes = petTypeService.findAll();

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
			petService.delete(petId);
			result = new ModelAndView("redirect:/pet/customer/list.do");
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

		String rootPath = System.getProperty("catalina.base");
		StringBuffer ruta = httpServletRequest.getRequestURL();
		String rutaf = StringUtils.replace(ruta.toString(),
				"pet/customer/details.do", "");

		String image = rutaf + "images" + "/pets/" + petId + ".png";

		result = new ModelAndView("pet/details");
		result.addObject("pet", pet);
		result.addObject("image", image);

		return result;
	}

	// Upload a image -------------------------------------------------------

	@RequestMapping(value = "/uploadImage", method = RequestMethod.GET)
	public ModelAndView uploadImage(int petId) {
		ModelAndView result;

		result = new ModelAndView("pet/uploadImage");
		result.addObject("petId", petId);

		return result;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(PetCustomerController.class);

	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST, params = "upload")
	public @ResponseBody
	String uploadFileHandler(@RequestParam("petId") int petId,
			@RequestParam("file") MultipartFile file,
			HttpServletRequest httpServletRequest) {

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.base");
				StringBuffer ruta = httpServletRequest.getRequestURL();
				String rutaf = StringUtils.replace(ruta.toString(),
						"pet/customer/details.do", "");
				File dir = new File(rutaf + "images" + "/pets");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + petId + ".png");
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location="
						+ serverFile.getAbsolutePath());

				return "You successfully uploaded file";

			} catch (Exception e) {
				return "You failed to upload => " + e.getMessage();
			}
		} else {
			return "The file is empty";
		}
	}
}
