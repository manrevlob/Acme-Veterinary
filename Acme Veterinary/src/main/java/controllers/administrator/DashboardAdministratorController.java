package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ClinicService;
import services.CustomerService;
import services.ItemService;
import services.VeterinaryService;
import domain.Customer;
import domain.Item;
import domain.Veterinary;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CustomerService customerService;
	@Autowired
	private VeterinaryService veterinaryService;
	@Autowired
	private ClinicService clinicService;
	@Autowired
	private ItemService itemService;

	// Constructors -----------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	// List----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		// Nivel C
		Integer numberNewCustomers = customerService.numberNewCustomers();
		Collection<Veterinary> vetMoreBusy = veterinaryService.vetMoreBusy();
		Collection<Veterinary> vetLessBusy = veterinaryService.vetLessBusy();
		Integer numberCustomers = customerService.numberCustomers();
		Collection<Object[]> clinicWithNumAppoint = clinicService
				.clinicWithNumAppoint();

		// Nivel B
		Collection<Customer> customerMorePay = customerService
				.customerMorePay();
		Collection<Item> mostDemandedItem = itemService.mostDemandedItem();
		Collection<Object[]> avgBulletinsByClinic = clinicService
				.avgBulletinsByClinic();

		// Nivel A
		Collection<Item> itemBestRating = itemService.itemBestRating();
		Collection<Object[]> avgComentPerCustomer = customerService
				.avgComentPerCustomer();
		Collection<Customer> customerMoreCommentDel = customerService
				.customerMoreCommentDel();
		Collection<Object[]> avgRatingPerVeterinary = veterinaryService
				.avgRatingPerVeterinary();
		Collection<Veterinary> bestFiveVeterinary = veterinaryService
				.bestFiveVeterinary();
		Collection<Object[]> customerSaveVoucher = customerService
				.customerSaveVoucher();

		result = new ModelAndView("administrator/dashboard");
		result.addObject("requestURI", "dashboard/administrator/list.do");
		// Nivel C
		result.addObject("numberNewCustomers", numberNewCustomers);
		result.addObject("vetMoreBusy", vetMoreBusy);
		result.addObject("vetLessBusy", vetLessBusy);
		result.addObject("numberCustomers", numberCustomers);
		result.addObject("clinicWithNumAppoint", clinicWithNumAppoint);

		// Nivel B
		result.addObject("customerMorePay", customerMorePay);
		result.addObject("mostDemandedItem", mostDemandedItem);
		result.addObject("avgBulletinsByClinic", avgBulletinsByClinic);

		// Nivel A
		result.addObject("itemBestRating", itemBestRating);
		result.addObject("avgComentPerCustomer", avgComentPerCustomer);
		result.addObject("customerMoreCommentDel", customerMoreCommentDel);
		result.addObject("avgRatingPerVeterinary", avgRatingPerVeterinary);
		result.addObject("bestFiveVeterinary", bestFiveVeterinary);
		result.addObject("customerSaveVoucher", customerSaveVoucher);

		return result;
	}
}
