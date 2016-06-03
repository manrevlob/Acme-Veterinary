package controllers.veterinary;

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
import services.HistoryService;
import services.PetService;
import controllers.AbstractController;
import domain.Appointment;
import domain.History;
import domain.Pet;
import forms.HistoryForm;

@Controller
@RequestMapping("/history/veterinary")
public class HistoryVeterinaryController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private HistoryService historyService;
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private PetService petService;
	
	// Constructors -----------------------------------------------------------

	public HistoryVeterinaryController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int petId) {
		ModelAndView result;

		Collection<History> histories;

		histories = historyService.findAllByPet(petId);
		Pet pet = petService.findOne(petId);

		result = new ModelAndView("history/list");
		result.addObject("requestURI", "history/veterinary/list.do");
		result.addObject("histories", histories);
		result.addObject("customerId", pet.getCustomer().getId());

		return result;
	}

	// Details --------------------------------------------------------------

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int historyId) {
		ModelAndView result;
		History history;

		history = historyService.findOne(historyId);

		result = new ModelAndView("history/details");
		result.addObject("requestURI", "history/veterinary/details.do");
		result.addObject("history", history);

		return result;

	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int appointmentId) {
		ModelAndView result;
		HistoryForm historyForm;
		Appointment appointment;

		appointment = appointmentService.findOne(appointmentId);
		
		if (historyService.checkOwn(appointment)){
			historyForm = new HistoryForm();
			historyForm.setAppointment(appointment);

			result = createAndEditModelAndView(historyForm);
		}else{
			result = new ModelAndView("misc/403");
		}
		
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid HistoryForm historyForm,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createAndEditModelAndView(historyForm);
		} else {
			try {

				if (!historyService.checkTreatment(historyForm)) {
					historyService.convertFormToHistory(historyForm);
					result = new ModelAndView(
							"redirect:/appointment/veterinary/list.do");
				} else if (historyService.checkTreatment(historyForm) && historyService.checkDates(historyForm)){
					historyService.convertFormToHistory(historyForm);
					result = new ModelAndView(
							"redirect:/appointment/veterinary/list.do");
				} else {
					result = createAndEditModelAndView(historyForm,
							"history.commit.errorDates");
				}
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
