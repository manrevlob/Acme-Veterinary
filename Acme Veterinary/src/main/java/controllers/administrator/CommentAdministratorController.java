package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BulletinService;
import services.ClinicService;
import services.CommentService;
import services.ItemService;
import controllers.AbstractController;
import domain.Clinic;
import domain.Item;
import forms.SearchForm;

@Controller
@RequestMapping("/comment/administrator")
public class CommentAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private CommentService commentService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ClinicService clinicService;
	@Autowired
	private BulletinService bulletinService;

	// Constructors -----------------------------------------------------------

	public CommentAdministratorController() {
		super();
	}

	// Delete comment -------------------------------------------------------

	@RequestMapping(value = "/deleteFromItem", method = RequestMethod.GET)
	public ModelAndView deleteFromItem(int commentId, int itemId) {
		ModelAndView result;
		try {
			commentService.delete(commentId);

			result = new ModelAndView("redirect:/comment/listByItem.do?itemId="+itemId);

		} catch (Throwable oops) {

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
			result.addObject("message", "comment.delete.error");
		}
		return result;
	}

	@RequestMapping(value = "/deleteFromVeterinary", method = RequestMethod.GET)
	public ModelAndView deleteFromVeterinary(int commentId, int veterinaryId) {
		ModelAndView result;
		Collection<Clinic> clinics;
		clinics = clinicService.findAllNotDeleted();
		try {

			commentService.delete(commentId);
			
			result = new ModelAndView("redirect:/comment/listByVeterinary.do?veterinaryId="+veterinaryId);
		} catch (Throwable oops) {

			result = new ModelAndView("clinic/list");
			result.addObject("requestURI", "clinic/list.do");
			result.addObject("clinics", clinics);
			result.addObject("message", "comment.delete.error");
		}
		return result;
	}

	@RequestMapping(value = "/deleteFromBulletin", method = RequestMethod.GET)
	public ModelAndView deleteFromBulletin(int commentId, int bulletinId) {
		ModelAndView result;
	
		try {
			
			commentService.delete(commentId);

			result = new ModelAndView("redirect:/comment/listByBulletin.do?bulletinId="+bulletinId);

		} catch (Throwable oops) {

			Collection<Object[]> bulletins;

			bulletins = bulletinService.findAll();

			result = new ModelAndView("bulletin/list");
			result.addObject("requestURI", "bulletin/list.do");
			result.addObject("bulletins", bulletins);
			result.addObject("message", "comment.delete.error");
		}
		return result;
	}

}
