/* WelcomeController.java
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/legal")
public class LegalController extends AbstractController {

	// Constructors -----------------------------------------------------------
	
	public LegalController() {
		super();
	}
		
	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/cookies")
	public ModelAndView cookies() {
		ModelAndView result;
		result = new ModelAndView("legal/cookies");		
		return result;
	}
	@RequestMapping(value = "/lopd")
	public ModelAndView lopd() {
		ModelAndView result;
		result = new ModelAndView("legal/lopd");		
		return result;
	}
}