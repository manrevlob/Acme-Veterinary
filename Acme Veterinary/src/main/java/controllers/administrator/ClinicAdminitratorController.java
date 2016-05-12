package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ClinicService;
import controllers.AbstractController;
import domain.Clinic;

@Controller
@RequestMapping("/clinic/administrator")
public class ClinicAdminitratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ClinicService clinicService;

	// Constructors -----------------------------------------------------------

	public ClinicAdminitratorController() {
		super();
	}

	// Create ------------------------------------------------------------------
			@RequestMapping(value = "/create", method = RequestMethod.GET)
			public ModelAndView create() {
				ModelAndView result;
				Clinic clinic;
				clinic = clinicService.create();
				
				result = new ModelAndView("clinic/create");
				result.addObject("clinic", clinic);
				return result;
			}

			// Edition ----------------------------------------------------------------

			@RequestMapping(value = "/edit", method = RequestMethod.GET)
			public ModelAndView edit(@Valid int clinicId) {
				ModelAndView result;

				Clinic clinic;
				clinic = clinicService.findOne(clinicId);
				result = new ModelAndView("clinic/edit");
				result.addObject("clinic", clinic);
				return result;
			}

			@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
			public ModelAndView save(@Valid Clinic clinic, BindingResult binding) {
				ModelAndView result;
				
				
				if (binding.hasErrors()) {
					result = new ModelAndView("clinic/edit");
				} else {
					try {
						//TODO comprobamos que sean de Flickr?
						//if(Utiles.checkURL(clinic.getPictures()) || clinic.getPictures()== ""){
							
							clinic = clinicService.save(clinic);
							result = new ModelAndView(
								"redirect:/clinic/list.do");
//						}else{
//							result = new ModelAndView("clinic/edit");
//							result.addObject("clinic", clinic);
//							result.addObject("message", "clinic.commit.errorURL");
//						}
//						
						
						
					} catch (Throwable oops) {
						result = new ModelAndView("clinic/edit");
						result.addObject("clinic", clinic);
						result.addObject("message", "clinic.commit.error");
					}
				}
				return result;
			}
			
			@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
			public ModelAndView delete(@Valid Clinic clinic, BindingResult binding) {
				ModelAndView result;

				if (binding.hasErrors()) {
					result = new ModelAndView("clinic/edit");

				} else {
					try {
						clinicService.delete(clinic);
						result = new ModelAndView(
									"redirect:/clinic/list.do");
						

					} catch (Throwable oops) {
						result = new ModelAndView("clinic/edit");
						result.addObject("clinic", clinic);
						result.addObject("message", "clinic.delete.serviceError");
					}
				}
				return result;
			}
			




}
