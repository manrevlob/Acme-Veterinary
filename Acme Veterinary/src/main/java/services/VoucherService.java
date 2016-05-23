package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.VoucherRepository;
import domain.Voucher;

@Service
@Transactional
public class VoucherService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private VoucherRepository voucherRepository;
	

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------

	public VoucherService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Voucher create() {
		Assert.isTrue(actorService.isAdministrator());
		Voucher result;
		result = new Voucher();
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
		return voucherRepository.save(voucher);
	}
	
	public void delete(Voucher voucher) {
		Assert.isTrue(actorService.isAdministrator());
		voucherRepository.delete(voucher);
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
	
	// Other business methods -------------------------------------------------
}
