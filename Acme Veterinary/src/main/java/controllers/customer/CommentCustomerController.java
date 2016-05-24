package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import controllers.AbstractController;
import domain.Comment;
import forms.CommentForm;

@Controller
@RequestMapping("/comment/customer")
public class CommentCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private CommentService commentService;

	// Constructors -----------------------------------------------------------

	public CommentCustomerController() {
		super();
	}

	// create Comment from an item
	// ----------------------------------------------------------------

	@RequestMapping(value = "/createToItem", method = RequestMethod.GET)
	public ModelAndView createToItem(int itemId) {
		ModelAndView result;

		CommentForm commentForm = commentService.createForm(itemId);
		result = new ModelAndView("comment/create");
		result.addObject("requestURI", "comment/customer/createToItem.do");
		result.addObject("commentForm", commentForm);

		return result;
	}

	@RequestMapping(value = "/createToItem", method = RequestMethod.POST, params = "save")
	public ModelAndView createToItem(@Valid CommentForm commentForm,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("comment/create");
			result.addObject("requestURI", "comment/customer/createToItem.do");
			result.addObject("commentForm", commentForm);

		} else {
			try {
				commentService.saveToItem(commentForm);
				result = new ModelAndView(
						"redirect:/comment/listByItem.do?itemId="
								+ commentForm.getId());

			} catch (Throwable oops) {

				Collection<Comment> comments;

				comments = commentService.findAllByItem(commentForm.getId());

				result = new ModelAndView("comment/list");
				result.addObject("comments", comments);
				result.addObject("itemId", commentForm.getId());
				result.addObject("message", "comment.commit.error");
			}
		}

		return result;
	}

	// create Comment from an item
	// ----------------------------------------------------------------

	@RequestMapping(value = "/createToVeterinary", method = RequestMethod.GET)
	public ModelAndView createToVeterinary(int veterinaryId) {
		ModelAndView result;

		CommentForm commentForm = commentService.createForm(veterinaryId);
		result = new ModelAndView("comment/create");
		result.addObject("requestURI", "comment/customer/createToVeterinary.do");
		result.addObject("commentForm", commentForm);

		return result;
	}

	@RequestMapping(value = "/createToVeterinary", method = RequestMethod.POST, params = "save")
	public ModelAndView createToVeterinary(@Valid CommentForm commentForm,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("comment/create");
			result.addObject("requestURI",
					"comment/customer/createToVeterinary.do");
			result.addObject("commentForm", commentForm);

		} else {
			try {
				commentService.saveToVeterinary(commentForm);
				result = new ModelAndView(
						"redirect:/comment/listByVeterinary.do?veterinaryId="
								+ commentForm.getId());

			} catch (Throwable oops) {

				Collection<Comment> comments;

				comments = commentService.findAllByItem(commentForm.getId());

				result = new ModelAndView("comment/list");
				result.addObject("comments", comments);
				result.addObject("itemId", commentForm.getId());
				result.addObject("message", "comment.commit.error");
			}
		}

		return result;
	}

}
