package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.VoucherRepository;
import domain.Customer;
import domain.Money;
import domain.Order;
import domain.Voucher;
import forms.VoucherForm;

@Service
@Transactional
public class VoucherService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private VoucherRepository voucherRepository;
	

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private OrderService orderService;
	
	// Constructors -----------------------------------------------------------

	public VoucherService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Voucher create() {
		Assert.isTrue(actorService.isAdministrator());
		Voucher result;
		result = new Voucher();
		result.setIsDeleted(false);
		return result;
	}

	public Voucher findOne(int voucherId) {
		Voucher result;
		result = voucherRepository.findOne(voucherId);
		return result;
	}

	public Collection<Voucher> findAll() {
		Assert.isTrue(actorService.isAdministrator());
		Collection<Voucher> result;
		result = voucherRepository.findAll();
		return result;
	}

	public Voucher save(Voucher voucher) {
		Assert.notNull(voucher);
		Assert.isTrue(actorService.isAdministrator());
		if (voucher.getId() == 0){
			checkVoucher(voucher);
		}
		return voucherRepository.save(voucher);
	}
	
	//Comprueba el Vocuher antes de crearlo
	private void checkVoucher(Voucher voucher) {
		//Comprobamos que el codigo no este ultilizado
		Voucher exits = voucherRepository.findByCode(voucher.getCode());
		if (exits != null){
			throw new IllegalArgumentException();
		}
	}

	public void delete(Voucher voucher) {
		Assert.isTrue(actorService.isAdministrator());
		voucher.setIsDeleted(true);
		voucherRepository.save(voucher);
	}

	//Comprobar que validUntil sea futuro
	public boolean checkValidUntil(Voucher voucher) {
		Assert.isTrue(actorService.isAdministrator());
		boolean res = false;
		Calendar c = Calendar.getInstance();
		if (voucher.getValidUntil().after(c.getTime())){
			res = true;
		}
		return res;
	}

	
	//Metodo que aplica el descuento sobre una order
	public VoucherForm applyVoucher(VoucherForm voucherForm) {
		Voucher voucher = findByCode(voucherForm.getCode());
		if (voucherForm.getVoucher() == null ){
			if (voucher != null){
				Integer voucherIsUsed = getVoucherIsUsedByPrincipal(voucher);
				if (voucherIsUsed == 0){
					Order order = orderService.voucherFormToOrder(voucherForm);
					if(order.getTotalPrice().getAmount() >= voucher.getMinimumOrder()){
						Money discount = new Money();
						discount.setAmount(order.getTotalPrice().getAmount() - voucher.getValue());
						discount.setCurrency("Euro");
						order.setTotalPrice(discount);
						voucherForm = orderService.orderToVoucherForm(order);
						voucherForm.setVoucher(voucher);
					}else{
						//El precio de la order es menor q el del minimo del cupon
						voucherForm.setMessage("error.voucher.noMinimumPrice");
					}
				}else{
					//el cliente ya lo ha usado
					voucherForm.setMessage("error.voucher.customerUsed");
				}
			}else{
				//no existe cupon
				voucherForm.setMessage("error.voucher.noExist");
			}
		}else{
			//ya ha usado un cupon
			voucherForm.setMessage("error.voucher.discountApplied");
		}
		return voucherForm;
	}

	private Integer getVoucherIsUsedByPrincipal(Voucher voucher) {
		Customer principal = customerService.findByPrincipal();
		Integer res;
		res = voucherRepository.getVoucherIsUsedByPrincipal(principal, voucher);
		return res;
	}

	private Voucher findByCode(String code) {
		Voucher result;
		result = voucherRepository.findByCode(code);
		return result;
	}

	//Comprueba que no sea nulo para el controlador
	public boolean isNotNull(Voucher voucher) {
		boolean result = false;
		if (voucher == null)
			result = true;
		
		return result;
		
	}
	
	// Other business methods -------------------------------------------------
}
