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
import services.PaymentService;
import utilities.Utiles;
import controllers.AbstractController;
import domain.Appointment;
import domain.Payment;

@Controller
@RequestMapping("/payment/veterinary")
public class PaymentVeterinaryController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private PaymentService paymentService;

	// Constructors -----------------------------------------------------------

	public PaymentVeterinaryController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int appointmentId) {
		ModelAndView result;

		Appointment appointment;
		appointment = appointmentService.findOne(appointmentId);

		Payment payment = paymentService.create(appointment);

		result = new ModelAndView("appointment/payment");
		result.addObject("payment", payment);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Payment payment, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("appointment/payment");
		} else {
			try {
				// Comprobamos que la tarjeta esta vacia o es valida
				if (Utiles.checkEmptyCreditCard((payment.getCreditCard()))
						|| Utiles.checkCreditCard(payment.getCreditCard())) {
					paymentService.save(payment);
					result = new ModelAndView(
							"redirect:/appointment/veterinary/details.do?appointmentId="
									+ payment.getAppointment().getId());
				} else {
					result = new ModelAndView("appointment/payment");
					result.addObject("payment", payment);
					result.addObject("message", "payment.creditcard.error");
				}
			} catch (Throwable oops) {
				result = new ModelAndView("appointment/payment");
				result.addObject("payment", payment);
				result.addObject("message", "payment.commit.error");
			}
		}

		return result;
	}
}
