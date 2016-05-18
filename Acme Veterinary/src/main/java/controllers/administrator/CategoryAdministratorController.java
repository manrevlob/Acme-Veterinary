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
import domain.Category;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private CategoryService categoryService;

	// Constructors -----------------------------------------------------------
	public CategoryAdministratorController() {
		super();
	}

	// List -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Category> categories;
		
		categories = categoryService.findAll();

		result = new ModelAndView("category/list");
		result.addObject("requestURI", "category/administrator/list.do");
		result.addObject("categories", categories);
		return result;
	}

	// Create ------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Category category = categoryService.create();

		result = new ModelAndView("category/create");
		result.addObject("category", category);
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@Valid int categoryId) {
		ModelAndView result;

		Category category;
		category = categoryService.findOne(categoryId);
		result = new ModelAndView("category/edit");
		result.addObject("category", category);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Category category, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("category/edit");
		} else {
			try {
				categoryService.save(category);
				result = new ModelAndView(
						"redirect:/category/administrator/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView("category/edit");
				result.addObject("category", category);
				result.addObject("message", "category.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid Category category, BindingResult binding) {
		ModelAndView result;
		boolean isDeleted;

		if (binding.hasErrors()) {
			result = new ModelAndView("category/edit");

		} else {
			try {
				isDeleted = categoryService.delete(category);
				if (isDeleted == true) {
					result = new ModelAndView(
							"redirect:/category/administrator/list.do");
				} else {
					result = new ModelAndView("category/edit");
					result.addObject("category", category);
					result.addObject("message", "category.delete.error");
				}

			} catch (Throwable oops) {
				result = new ModelAndView("category/edit");
				result.addObject("category", category);
				result.addObject("message", "category.delete.serviceError");
			}
		}
		return result;
	}
}
