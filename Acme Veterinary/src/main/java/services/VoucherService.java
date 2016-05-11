package services;

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

	// Constructors -----------------------------------------------------------

	public VoucherService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Voucher create() {
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
		Collection<Voucher> result;
		result = voucherRepository.findAll();
		return result;
	}

	public Voucher save(Voucher voucher) {
		Assert.notNull(voucher);
		return voucherRepository.save(voucher);
	}
	
	public void delete(Voucher voucher) {
		voucherRepository.delete(voucher);
	}
	
	// Other business methods -------------------------------------------------
}
