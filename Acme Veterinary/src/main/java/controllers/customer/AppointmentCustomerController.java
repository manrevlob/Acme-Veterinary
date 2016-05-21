package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AppointmentService;
import services.PetService;
import controllers.AbstractController;
import domain.Appointment;
import domain.Pet;
import forms.AppointmentForm;

@Controller
@RequestMapping("/appointment/customer")
public class AppointmentCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private PetService petService;
	
	// Constructors -----------------------------------------------------------

	public AppointmentCustomerController() {
		super();
	}

	
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false) String message) {
		ModelAndView result;

		Collection<Appointment> appointments = appointmentService.findByPrincipalNoExpired();
		
		result = new ModelAndView("appointment/list");
		result.addObject("requestURI", "appointment/customer/list.do");
		result.addObject("appointments", appointments);
		result.addObject("message", message);

		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "book")
	public ModelAndView create(@Valid AppointmentForm appointmentForm, BindingResult binding) {
		ModelAndView result;
		
		Collection<Pet> pets = petService.findAllByPrincipal();
		
		result = new ModelAndView("appointment/create");
		result.addObject("appointmentForm", appointmentForm);
		result.addObject("pets", pets);

		return result;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid AppointmentForm appointmentForm, BindingResult binding) {
		ModelAndView result;
		
		
		if (binding.hasErrors()) {
			result = new ModelAndView("appointment/create");
			result.addObject("appointmentForm", "appointmentForm");
		} else {
			try {
				Appointment appointment= appointmentService.reconstruct(appointmentForm);
				appointmentService.save(appointment);
				result = new ModelAndView("redirect:/appointment/customer/list.do");
				
			} catch (Throwable oops) {
				result = new ModelAndView("appointment/create");
				result.addObject("appointmentForm", "appointmentForm");
			}
		}

		return result;
	}
	
	@RequestMapping("/cancel")
	public ModelAndView cancel(int appointmentId) {
		ModelAndView result;

		
		try {
			appointmentService.cancelAppointment(appointmentId);
			result = new ModelAndView("redirect:/appointment/customer/list.do");
		} catch (Exception e) {
			result = new ModelAndView("redirect:/appointment/customer/list.do?message=appointment.error");
		}
		
		
		return result;
	}
}
