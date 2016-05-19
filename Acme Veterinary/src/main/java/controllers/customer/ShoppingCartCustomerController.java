package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ItemService;
import services.ShoppingCartLineService;
import services.ShoppingCartService;
import controllers.AbstractController;
import domain.Item;
import domain.ShoppingCart;
import domain.ShoppingCartLine;

@Controller
@RequestMapping("/shoppingCart/customer")
public class ShoppingCartCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private ShoppingCartLineService shoppingCartLineService;
	@Autowired
	private ItemService itemService;

	// Display ---------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		ShoppingCart shoppingCart;
		Double total = 0.0;
		Collection<ShoppingCartLine> shoppingCartLines;
		String coin = "Euro";
		
		shoppingCart = shoppingCartService.findByCustomerPrincipal();
		shoppingCartLines = shoppingCartLineService
				.findByShoppingCart(shoppingCart);

		for (ShoppingCartLine line : shoppingCartLines) {
			total += line.getItem().getPrice().getAmount()
					* line.getQuantity();
		}

		result = new ModelAndView("shoppingCart/show");
		result.addObject("shoppingCartLines", shoppingCartLines);
		result.addObject("shoppingCart", shoppingCart);
		result.addObject("requestURI", "shoppingCart/customer/show.do");
		result.addObject("total", total);
		result.addObject("coin", coin);

		return result;
	}

	// Add -------------------------------------------------------------------

	@RequestMapping(value = "/addItem", method = RequestMethod.GET)
	public ModelAndView addItem(@Valid int itemId) {
		ModelAndView result;
		Item item;
		Integer quantity;

		quantity = 1;
		item = itemService.findOne(itemId);
		shoppingCartService.addItem(item, quantity);

		result = new ModelAndView("redirect:/shoppingCart/customer/show.do");

		return result;
	}

	// More or Less ----------------------------------------------------------

	@RequestMapping(value = "/more", method = RequestMethod.GET)
	public ModelAndView moreItem(@Valid int shoppingCartLineId) {
		ModelAndView result;
		ShoppingCartLine shoppingCartLine;
		Integer quantity;

		shoppingCartLine = shoppingCartLineService.findOne(shoppingCartLineId);
		quantity = shoppingCartLine.getQuantity() + 1;
		shoppingCartService.modifyQuantityOfItem(shoppingCartLine.getItem(),
				quantity);

		result = new ModelAndView("redirect:/shoppingCart/customer/show.do");

		return result;
	}

	@RequestMapping(value = "/less", method = RequestMethod.GET)
	public ModelAndView lessItem(@Valid int shoppingCartLineId) {
		ModelAndView result;
		ShoppingCartLine shoppingCartLine;
		Integer quantity;

		shoppingCartLine = shoppingCartLineService.findOne(shoppingCartLineId);

		if (shoppingCartLine.getQuantity() >= 2) {
			quantity = shoppingCartLine.getQuantity() - 1;
			shoppingCartService.modifyQuantityOfItem(
					shoppingCartLine.getItem(), quantity);
		}

		result = new ModelAndView("redirect:/shoppingCart/customer/show.do");

		return result;
	}

	// Delete ---------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteItem(@Valid int shoppingCartLineId) {
		ModelAndView result;
		ShoppingCartLine shoppingCartLine;

		shoppingCartLine = shoppingCartLineService.findOne(shoppingCartLineId);

		if (shoppingCartService.findByCustomerPrincipal().getShoppingCartLines().size() == 1){
			shoppingCartService.findByCustomerPrincipal().setComment(null);
			shoppingCartLineService.delete(shoppingCartLine);
		}else{
			shoppingCartLineService.delete(shoppingCartLine);
		}

		result = new ModelAndView("redirect:/shoppingCart/customer/show.do");

		return result;
	}

	// AddComment -------------------------------------------------------------

	@RequestMapping(value = "/addComment", method = RequestMethod.GET)
	public ModelAndView addComment() {
		ModelAndView result;
		ShoppingCart shoppingCart;

		shoppingCart = shoppingCartService.findByCustomerPrincipal();

		result = new ModelAndView("shoppingCart/addComment");
		result.addObject("shoppingCart", shoppingCart);

		return result;
	}

	@RequestMapping(value = "/addComment", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ShoppingCart shoppingCart,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("shoppingCart/addComment");
		} else {
			try {
				shoppingCartService.save(shoppingCart);
				result = new ModelAndView(
						"redirect:/shoppingCart/customer/show.do");
			} catch (Throwable oops) {
				result = new ModelAndView("shoppingCart/addComment");
				result.addObject("shoppingCart", shoppingCart);
				result.addObject("message", "shoppingCart.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/addComment", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid ShoppingCart shoppingCart,
			BindingResult binding) {
		ModelAndView result;

		try {
			shoppingCart.setComment(null);
			shoppingCartService.save(shoppingCart);
			result = new ModelAndView("redirect:/shoppingCart/customer/show.do");
		} catch (Throwable oops) {
			result = new ModelAndView("shoppingCart/addComment");
			result.addObject("shoppingCart", shoppingCart);
			result.addObject("message", "shoppingCart.commit.error");
		}

		return result;
	}

}
