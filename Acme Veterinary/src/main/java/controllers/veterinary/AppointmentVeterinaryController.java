package controllers.veterinary;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AppointmentService;
import controllers.AbstractController;
import domain.Appointment;

@Controller
@RequestMapping("/appointment/veterinary")
public class AppointmentVeterinaryController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AppointmentService appointmentService;

	// Constructors -----------------------------------------------------------

	public AppointmentVeterinaryController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<Appointment> appointments;

		appointments = appointmentService.findAllOwn();

		result = new ModelAndView("appointment/list");
		result.addObject("requestURI", "appointment/veterinary/list.do");
		result.addObject("appointments", appointments);

		return result;
	}

	// Details -------------------------------------------------------------------
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int appointmentId) {
		ModelAndView result;

		Appointment appointment;

		appointment = appointmentService.findOne(appointmentId);

		result = new ModelAndView("appointment/details");
		result.addObject("requestURI", "appointment/veterinary/details.do");
		result.addObject("appointment", appointment);

		return result;
	}

}
