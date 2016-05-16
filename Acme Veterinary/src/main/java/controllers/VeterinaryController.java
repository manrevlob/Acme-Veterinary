/* CustomerController.java
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
import org.springframework.web.servlet.ModelAndView;

import services.VeterinaryService;
import domain.Veterinary;

@Controller
@RequestMapping("/veterinary")
public class VeterinaryController extends AbstractController {

	// Constructors -----------------------------------------------------------
	public VeterinaryController() {
		super();
	}

	// Services ---------------------------------------------------------------

	@Autowired
	private VeterinaryService veterinaryService;

	

	// List  ---------------------------------------------------------------		

	@RequestMapping("/list")
	public ModelAndView list(int clinicId) {
		ModelAndView result;

		Collection<Veterinary> veterinaries = veterinaryService.findByClinic(clinicId);
		
		result = new ModelAndView("veterinary/list");
		result.addObject("requestURI", "veterinary/list.do");
		result.addObject("veterinaries", veterinaries);

		return result;
	}
	
	@RequestMapping("/appointment")
	public ModelAndView appointment(int veterinaryId) {
		ModelAndView result;
	
		result = new ModelAndView("veterinary/appointment");
		result.addObject("requestURI", "veterinary/appointment.do");

		return result;
	}
	
}