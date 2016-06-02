/* ProfileController.java
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ClinicService;
import domain.Clinic;

@Controller
@RequestMapping("/clinic")
public class ClinicController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ClinicService clinicService;

	// Constructors -----------------------------------------------------------

	public ClinicController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<Clinic> clinics;

		clinics = clinicService.findAllNotDeleted();

		result = new ModelAndView("clinic/list");
		result.addObject("requestURI", "clinic/list.do");
		result.addObject("clinics", clinics);

		return result;
	}

	// Details --------------------------------------------------------------

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int clinicId) {
		ModelAndView result;
		Clinic clinic;

		clinic = clinicService.findOne(clinicId);

		result = new ModelAndView("clinic/details");
		result.addObject("requestURI", "clinic/details.do");
		result.addObject("clinic", clinic);

		return result;

	}

}