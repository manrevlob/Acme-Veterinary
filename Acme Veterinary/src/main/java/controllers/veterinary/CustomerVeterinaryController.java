package controllers.veterinary;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.PetService;
import controllers.AbstractController;
import domain.Customer;
import domain.Pet;
import forms.SearchForm;

@Controller
@RequestMapping("/customer/veterinary")
public class CustomerVeterinaryController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CustomerService customerService;
	@Autowired
	private PetService petService;

	// Constructors -----------------------------------------------------------

	public CustomerVeterinaryController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		SearchForm searchForm;
		Collection<Customer> customers;

		customers = customerService.findAll();
		searchForm = new SearchForm();

		result = new ModelAndView("customer/list");
		result.addObject("requestURI", "customer/veterinary/list.do");
		result.addObject("customers", customers);
		result.addObject("searchForm", searchForm);

		return result;
	}
	
	// List By pets --------------------------------------------------------
	
	@RequestMapping(value = "/listPet", method = RequestMethod.GET)
	public ModelAndView listPet(@RequestParam int customerId) {
		ModelAndView result;
		Collection<Pet> pets;

		pets = petService.findAllByCustomer(customerId);

		result = new ModelAndView("pet/list");
		result.addObject("requestURI", "pet/veterinary/listPet.do");
		result.addObject("pets", pets);

		return result;
	}

	// Search ------------------------------------------------------------

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "search")
	public ModelAndView search(@Valid SearchForm searchForm) {
		ModelAndView result;
		Collection<Customer> customers;
		SearchForm newSearchForm;
		String keyword;

		newSearchForm = new SearchForm();

		keyword = searchForm.getKeyword();
		customers = customerService.findByKeyword(keyword);

		result = new ModelAndView("customer/list");
		result.addObject("searchForm", newSearchForm);
		result.addObject("requestURI", "customer/veterinary/listSearched.do");
		result.addObject("customers", customers);
		return result;
	}

}
