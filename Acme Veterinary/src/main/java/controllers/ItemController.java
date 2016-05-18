package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ItemService;
import domain.Item;
import forms.SearchForm;

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ItemService itemService;

	// Constructors -----------------------------------------------------------

	public ItemController() {
		super();
	}

	// List ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Item> items;
		SearchForm searchForm;
		String coin;

		items = itemService.findAllNoDeleted();
		searchForm = new SearchForm();
		coin = "Euro";
		
		result = new ModelAndView("item/list");
		result.addObject("requestURI", "item/list.do");
		result.addObject("searchForm", searchForm);
		result.addObject("items", items);
		result.addObject("coin", coin);
		return result;

	}

	// Search ------------------------------------------------------------

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "search")
	public ModelAndView search(@Valid SearchForm searchForm) {
		ModelAndView result;
		Collection<Item> items;
		SearchForm newSearchForm;
		String keyword;
		String coin;

		newSearchForm = new SearchForm();

		keyword = searchForm.getKeyword();
		items = itemService.findByKeyword(keyword);
		coin = "Euro";

		result = new ModelAndView("item/list");
		result.addObject("searchForm", newSearchForm);
		result.addObject("requestURI", "item/list.do");
		result.addObject("items", items);
		result.addObject("coin", coin);

		return result;
	}

}