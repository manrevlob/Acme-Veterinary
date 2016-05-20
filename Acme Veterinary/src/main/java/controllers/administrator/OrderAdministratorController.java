package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ItemOrderService;
import services.OrderService;
import controllers.AbstractController;
import domain.ItemOrder;
import domain.Order;

@Controller
@RequestMapping("/order/administrator")
public class OrderAdministratorController extends AbstractController {
	// Services ---------------------------------------------------------------

	@Autowired
	private OrderService orderService;
	@Autowired
	private ItemOrderService itemOrderService;

	// Constructors -----------------------------------------------------------

	public OrderAdministratorController() {
		super();
	}

	// Listing -----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Order> orders;
		orders = orderService.findAllNotCanceled();

		result = new ModelAndView("order/list");
		result.addObject("requestURI", "order/administrator/list.do");
		result.addObject("orders", orders);
		return result;
	}

	// Details ------------------------------------------------------------
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int orderId) {
		ModelAndView result;
		Order order;
		Collection<ItemOrder> itemOrders;

		order = orderService.findOne(orderId);
		itemOrders = itemOrderService.findByOrder(orderId);

		result = new ModelAndView("order/details");
		result.addObject("order", order);
		result.addObject("itemOrders", itemOrders);
		result.addObject("requestURI", "order/customer/details.do?orderId="
				+ order.getId());

		return result;
	}

	// Cancel -----------------------------------------------------------
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancelOrder(@Valid int orderId) {
		ModelAndView result;
		Order order = orderService.findOne(orderId);
		Collection<ItemOrder> itemOrders;

		itemOrders = itemOrderService.findByOrder(orderId);

		try {

			orderService.cancelOrderAdminstrator(order);

			result = new ModelAndView("redirect:/order/administrator/list.do");

		} catch (Exception oops) {
			result = new ModelAndView("order/details");
			result.addObject("order", order);
			result.addObject("itemOrders", itemOrders);
			result.addObject("requestURI", "order/customer/details.do?orderId="
					+ order.getId());
			result.addObject("message", "order.cancel.serviceError");
		}

		return result;
	}

}
