package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.ItemOrderService;
import services.OrderService;
import services.ShoppingCartService;
import controllers.AbstractController;
import domain.ItemOrder;
import domain.Order;

@Controller
@RequestMapping("/order/customer")
public class OrderCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private OrderService orderService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private ItemOrderService itemOrderService;
	@Autowired
	private CustomerService customerService;

	// Constructors -----------------------------------------------------------
	public OrderCustomerController() {
		super();
	}

	// List -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Order> orders;
		orders = orderService.findAllByCustomer();

		result = new ModelAndView("order/list");
		result.addObject("requestURI", "/order/consumer/list.do");
		result.addObject("orders", orders);
		return result;

	}

	// // Creation -----------------------------------------------------------
	//
	// @RequestMapping(value = "/create", method = RequestMethod.GET)
	// public ModelAndView create() {
	// ModelAndView result;
	// Order order;
	// ShoppingCart shoppingCart;
	//
	// shoppingCart = shoppingCartService.findByCustomerPrincipal();
	// order = orderService.create();
	//
	// order.setComment(shoppingCart.getComment());
	// order.setConsumer(consumerService.findByPrincipal());
	// order.setIsCanceled(false);
	// order.setMoment(new Date(System.currentTimeMillis() - 100));
	// order.setTicker(orderService.generateTicker());
	// order.setTotalPrice(shoppingCartService.calculateTotalPrice());
	//
	// result = new ModelAndView("order/edit");
	// result.addObject("order", order);
	// return result;
	// }
	//
	// @RequestMapping(value = "/edit", method = RequestMethod.POST, params =
	// "save")
	// public ModelAndView save(@Valid Order order, BindingResult binding) {
	// ModelAndView result;
	//
	// if (binding.hasErrors()) {
	// result = new ModelAndView("order/edit");
	// result.addObject("order", order);
	// result.addObject("message", "order.commit.error");
	// } else {
	// try {
	// if (order.getCreditCard().getNumber() != "") {
	// if (order.getCreditCard().getCvv() != null) {
	// order.setTotalPrice(shoppingCartService
	// .calculateTotalPrice());
	// order = orderService.save(order);
	// itemOrderService.importShoppingCartLines(
	// shoppingCartService.findByConsumerPrincipal(),
	// order);
	// shoppingCartService.delete(shoppingCartService
	// .findByConsumerPrincipal());
	// deliveryService.generateDelivery(order);
	//
	// result = new ModelAndView(
	// "redirect:/order/consumer/list.do");
	// } else {
	// result = new ModelAndView("order/edit");
	// result.addObject("order", order);
	// result.addObject("message", "order.commit.CVVError");
	// }
	// } else {
	// result = new ModelAndView("order/edit");
	// result.addObject("order", order);
	// result.addObject("message", "order.commit.CCerror");
	// }
	//
	// } catch (Throwable oops) {
	// result = new ModelAndView("order/edit");
	// result.addObject("order", order);
	// result.addObject("message", "order.commit.date");
	// }
	// }
	// return result;
	// }
	//
	// // Details -----------------------------------------------------------
	//
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int orderId) {
		ModelAndView result;
		Order order;
		Collection<ItemOrder> itemOrders;

		order = orderService.findOne(orderId);
		if (customerService.findByPrincipal().getOrders().contains(order)) {
			itemOrders = itemOrderService.findByOrder(orderId);

			result = new ModelAndView("order/details");
			result.addObject("order", order);
			result.addObject("itemOrders", itemOrders);
			result.addObject("requestURI", "order/customer/details.do?orderId="
					+ order.getId());
		} else {
			result = new ModelAndView("misc/403");
		}
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
			if (orderService.canBeCanceled(order)) {
				orderService.cancelOrderCustomer(order);
				result = new ModelAndView("redirect:/order/customer/list.do");
			} else {
				result = new ModelAndView("order/details");
				result.addObject("order", order);
				result.addObject("itemOrders", itemOrders);
				result.addObject("requestURI",
						"order/customer/details.do?orderId=" + order.getId());
				result.addObject("message", "order.cantBeCanceled.error");
			}
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
