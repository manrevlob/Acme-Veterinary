package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.ItemService;
import controllers.AbstractController;
import domain.Category;
import domain.Item;

@Controller
@RequestMapping("/item/administrator")
public class ItemAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ItemService itemService;
	@Autowired
	private CategoryService categoryService;

	// Constructors -----------------------------------------------------------

	public ItemAdministratorController() {
		super();
	}

	// List ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Item> items;
		String coin;

		items = itemService.findAll();
		coin = "Euro";

		result = new ModelAndView("item/list");
		result.addObject("requestURI", "item/administrator/list.do");
		result.addObject("items", items);
		result.addObject("coin", coin);
		return result;

	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Item item;
		Collection<Category> categories;
		
		item = itemService.create();
		categories = categoryService.findAll();

		result = new ModelAndView("item/create");
		result.addObject("item", item);
		result.addObject("categories", categories);
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@Valid int itemId) {
		ModelAndView result;
		Item item;
		Collection<Category> categories;

		item = itemService.findOne(itemId);
		categories = categoryService.findAll();

		result = new ModelAndView("item/edit");
		result.addObject("item", item);
		result.addObject("categories", categories);
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Item item, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("item/edit");
		} else {
			try {
				itemService.save(item);
				result = new ModelAndView("redirect:/item/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView("item/edit");
				result.addObject("item", item);
				result.addObject("message", "item.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid Item item, BindingResult binding) {
		ModelAndView result;

		try {
			itemService.delete(item);
			result = new ModelAndView("redirect:/item/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("item/edit");
			result.addObject("item", item);
			result.addObject("message", "item.commit.error");
		}

		return result;
	}

}
