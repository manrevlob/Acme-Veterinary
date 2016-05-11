package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PaymentRepository;
import domain.Payment;

@Service
@Transactional
public class PaymentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PaymentRepository paymentRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public PaymentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Payment create() {
		Payment result;
		result = new Payment();
		return result;
	}

	public Payment findOne(int paymentId) {
		Payment result;
		result = paymentRepository.findOne(paymentId);
		return result;
	}

	public Collection<Payment> findAll() {
		Collection<Payment> result;
		result = paymentRepository.findAll();
		return result;
	}

	public Payment save(Payment payment) {
		Assert.notNull(payment);
		return paymentRepository.save(payment);
	}
	
	public void delete(Payment payment) {
		paymentRepository.delete(payment);
	}
	
	// Other business methods -------------------------------------------------
}
