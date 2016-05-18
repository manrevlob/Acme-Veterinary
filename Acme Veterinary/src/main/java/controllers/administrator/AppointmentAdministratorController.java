package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AppointmentService;
import services.VeterinaryService;
import controllers.AbstractController;
import domain.Appointment;
import domain.Veterinary;

@Controller
@RequestMapping("/appointment/administrator")
public class AppointmentAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private VeterinaryService veterinaryService;
	
	@Autowired
	private AppointmentService appointmentService;

	// Constructors -----------------------------------------------------------

	public AppointmentAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) String message, int veterinaryId) {
		ModelAndView result;

		Collection<Appointment> appointments;

		Veterinary veterinary = veterinaryService.findOne(veterinaryId);
		appointments = appointmentService.findAllOwnNoExpired(veterinary);

		result = new ModelAndView("appointment/list");
		result.addObject("requestURI", "appointment/veterinary/list.do");
		result.addObject("appointments", appointments);
		result.addObject("message", message);

		return result;
	}

	@RequestMapping("/cancel")
	public ModelAndView cancel(int appointmentId) {
		ModelAndView result;

		try {
			Appointment appointment = appointmentService.findOne(appointmentId);
			appointmentService.cancelAppointmentAdministrator(appointmentId);
			result = new ModelAndView("redirect:/appointment/administrator/list.do?veterinaryId="+appointment.getVeterinary().getId());
		} catch (Exception e) {
			Appointment appointment = appointmentService.findOne(appointmentId);
			result = new ModelAndView("redirect:/appointment/administrator/list.do?veterinaryId="+appointment.getVeterinary().getId()+"&message=appointment.error");
		}
		
		
		return result;
	}
}
