package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BulletinService;
import services.CommentService;
import services.VeterinaryService;
import domain.Bulletin;
import domain.Comment;
import domain.Veterinary;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private CommentService commentService;
	@Autowired
	private VeterinaryService veterinaryService;
	@Autowired
	private BulletinService bulletinService;

	// Constructors -----------------------------------------------------------
	public CommentController() {
		super();
	}

	// Listing -----------------------------------------------------------

	@RequestMapping(value = "/listByItem", method = RequestMethod.GET)
	public ModelAndView listItem(@Valid int itemId) {
		ModelAndView result;
		Collection<Comment> comments;

		comments = commentService.findAllByItem(itemId);

		result = new ModelAndView("comment/list");
		result.addObject("comments", comments);
		result.addObject("itemId", itemId);
		result.addObject("requestURI", "comment/listByItem.do");

		return result;

	}

	// Listing -----------------------------------------------------------

	@RequestMapping(value = "/listByVeterinary", method = RequestMethod.GET)
	public ModelAndView listVeterinary(@Valid int veterinaryId) {
		ModelAndView result;
		Collection<Comment> comments;

		Veterinary veterinary = veterinaryService.findOne(veterinaryId);
		comments = commentService.findAllByVeterinary(veterinaryId);

		result = new ModelAndView("comment/list");
		result.addObject("comments", comments);
		result.addObject("veterinaryId", veterinaryId);
		result.addObject("clinicId", veterinary.getClinic().getId());
		result.addObject("requestURI", "comment/listByVeterinary.do");

		return result;

	}

	@RequestMapping(value = "/listByBulletin", method = RequestMethod.GET)
	public ModelAndView listBulletin(@Valid int bulletinId) {
		ModelAndView result;
		Collection<Comment> comments;
		Bulletin bulletin = bulletinService.findOne(bulletinId);
		comments = commentService.findAllByBulletin(bulletinId);

		result = new ModelAndView("comment/list");
		result.addObject("comments", comments);
		result.addObject("bulletinId", bulletinId);
		result.addObject("clinicId", bulletin.getClinic().getId());
		result.addObject("requestURI", "comment/listByBulletin.do");

		return result;

	}

}
