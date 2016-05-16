package controllers;

import java.util.Date;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AppointmentService;
import services.VeterinaryService;
import domain.Veterinary;
import forms.AppointmentForm;

@Controller
@RequestMapping("/appointment")
public class AppointmentController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private VeterinaryService veterinaryService;
	
	// Constructors -----------------------------------------------------------

	public AppointmentController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list")
	public ModelAndView create(@RequestParam(defaultValue = "0", required = true) int veterinaryId) {
		ModelAndView result;
		AppointmentForm appointmentForm = new AppointmentForm();
		Veterinary veterinary = veterinaryService.findOne(veterinaryId);
		appointmentForm.setVeterinary(veterinary);
		String day = appointmentService.getDay();
		result = createModel(day, appointmentForm, veterinary);

		return result;
	}

	// Aqui entraremos desde la vista de de horarios al cambiar la fecha
	@RequestMapping(value = "/listDate", method = RequestMethod.POST, params = "datedate")
	public ModelAndView createDate(AppointmentForm appointmentForm) {
		ModelAndView result;
		result = createModel(appointmentForm.getStartMoment().toString(), appointmentForm,
				appointmentForm.getVeterinary());
		return result;
	}

	private ModelAndView createModel(String day,
			AppointmentForm appointmentForm, Veterinary veterinary) {
		ModelAndView result;
		LinkedHashMap<String, String> days = appointmentService.getNextDays();

		result = new ModelAndView("veterinary/appointment");
		result.addObject("appointmentService", appointmentService);
		result.addObject("appointmentForm", appointmentForm);
		result.addObject("veterinary", veterinary);
		result.addObject("days", days);
		result.addObject("daySelected", day);

		return result;
	}

}
