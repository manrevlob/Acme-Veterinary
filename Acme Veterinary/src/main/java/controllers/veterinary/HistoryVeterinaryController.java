package controllers.veterinary;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AppointmentService;
import services.HistoryService;
import controllers.AbstractController;
import domain.Appointment;
import forms.HistoryForm;

@Controller
@RequestMapping("/history/veterinary")
public class HistoryVeterinaryController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private HistoryService historyService;
	@Autowired
	private AppointmentService appointmentService;

	// Constructors -----------------------------------------------------------

	public HistoryVeterinaryController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int appointmentId) {
		ModelAndView result;
		HistoryForm historyForm;
		Appointment appointment;

		appointment = appointmentService.findOne(appointmentId);
		historyForm = new HistoryForm();
		historyForm.setAppointment(appointment);

		result = createAndEditModelAndView(historyForm);
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid HistoryForm historyForm, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createAndEditModelAndView(historyForm);
		} else {
			try {
				historyService.convertFormToHistory(historyForm);
				result = new ModelAndView("redirect:/appointment/veterinary/list.do");
			} catch (Throwable oops) {
				result = createAndEditModelAndView(historyForm,
						"history.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createAndEditModelAndView(HistoryForm historyForm) {
		ModelAndView result;

		result = createAndEditModelAndView(historyForm, null);

		return result;
	}

	protected ModelAndView createAndEditModelAndView(HistoryForm historyForm,
			String message) {
		ModelAndView result;

		result = new ModelAndView("history/edit");
		result.addObject("historyForm", historyForm);
		result.addObject("message", message);
		return result;
	}

}
