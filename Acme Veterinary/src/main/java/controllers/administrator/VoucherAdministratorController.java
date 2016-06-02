package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.VoucherService;
import controllers.AbstractController;
import domain.Voucher;

@Controller
@RequestMapping("/voucher/administrator")
public class VoucherAdministratorController extends AbstractController {
	// Services ---------------------------------------------------------------

	@Autowired
	private VoucherService voucherService;

	// Constructors -----------------------------------------------------------

	public VoucherAdministratorController() {
		super();
	}

	// Listing -----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Voucher> vouchers;
		vouchers = voucherService.findAll();

		result = new ModelAndView("voucher/list");
		result.addObject("requestURI", "voucher/administrator/list.do");
		result.addObject("vouchers", vouchers);
		return result;
	}
	
	//Create
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Voucher voucher;
		voucher = voucherService.create();
		
		result = new ModelAndView("voucher/create");
		result.addObject("voucher", voucher);
		return result;
	}
	
	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@Valid int voucherId) {
		ModelAndView result;

		Voucher voucher;
		voucher = voucherService.findOne(voucherId);
		if (voucherService.isNotNull(voucher)){
			result = new ModelAndView("misc/403");
		}else{
			result = new ModelAndView("voucher/edit");
			result.addObject("voucher", voucher);
		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Voucher voucher, BindingResult binding) {
		ModelAndView result;
		
		
		if (binding.hasErrors()) {
			result = new ModelAndView("voucher/edit");
			result.addObject("voucher",voucher);
		} else {
			if (voucherService.checkValidUntil(voucher)){
				try {
					voucher = voucherService.save(voucher);
					result = new ModelAndView("redirect:/voucher/administrator/list.do");
	
				} catch (IllegalArgumentException ill){
					result = new ModelAndView("voucher/edit");
					result.addObject("voucher", voucher);
					result.addObject("message", "voucher.commit.error.sameCode");
				} catch (Throwable oops) {
					result = new ModelAndView("voucher/edit");
					result.addObject("voucher", voucher);
					result.addObject("message", "voucher.commit.error");
				}
			}else{
				result = new ModelAndView("voucher/edit");
				result.addObject("voucher", voucher);
				result.addObject("message", "voucher.commit.validUntil");
			}
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
