package controllers.customer;

import java.util.LinkedHashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.OrderService;
import services.VoucherService;
import utilities.Utiles;
import controllers.AbstractController;
import domain.Order;
import domain.Voucher;
import forms.VoucherForm;

@Controller
@RequestMapping("/voucher/customer")
public class VoucherCustomerController extends AbstractController {
	// Services ---------------------------------------------------------------

	@Autowired
	private VoucherService voucherService;
	@Autowired
	private OrderService orderService;

	// Constructors -----------------------------------------------------------

	public VoucherCustomerController() {
		super();
	}

	@RequestMapping(value = "/apply", method = RequestMethod.POST, params = "apply")
	public ModelAndView apply(@Valid VoucherForm voucherForm,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("order/edit");
			Order order = orderService.voucherFormToOrder(voucherForm);
			result.addObject("order", order);
			result.addObject("voucherForm", voucherForm);
			LinkedHashMap<String, Integer> months = Utiles.getMonth();
			LinkedHashMap<String, Integer> years = Utiles.getYears();
			result.addObject("months", months);
			result.addObject("years", years);
		} else {
			voucherForm = voucherService.applyVoucher(voucherForm);
			result = new ModelAndView("order/edit");
			Order order = orderService.voucherFormToOrder(voucherForm);
			result.addObject("order", order);
			result.addObject("voucherForm", voucherForm);
			LinkedHashMap<String, Integer> months = Utiles.getMonth();
			LinkedHashMap<String, Integer> years = Utiles.getYears();
			result.addObject("months", months);
			result.addObject("years", years);
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid Voucher voucher) {
		ModelAndView result;

		try {
			voucherService.delete(voucher);
			result = new ModelAndView("redirect:/voucher/administrator/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("voucher/edit");
			result.addObject("voucher", voucher);
			result.addObject("message", "voucher.delete.serviceError");
		}

		return result;
	}

}
