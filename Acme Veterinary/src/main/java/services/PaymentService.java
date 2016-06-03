package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PaymentRepository;
import utilities.Utiles;
import domain.Appointment;
import domain.Money;
import domain.Payment;

@Service
@Transactional
public class PaymentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private ActorService actorService;
	@Autowired
	private AppointmentService appointmentService;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public PaymentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Payment create(Appointment appointment) {
		Assert.isTrue(appointment.getPayment() == null);
		Assert.isTrue(!appointmentService.checkAppointment(appointment));
		Payment result;
		result = new Payment();
		result.setMoment(new Date(System.currentTimeMillis() - 1000));
		result.setAppointment(appointment);
		Money money = new Money();
		money.setCurrency("Euros");
		result.setTotalCost(money);
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
		Assert.isTrue(actorService.isVeterinary());
		Assert.notNull(payment);
		Assert.isTrue(payment.getTotalCost().getAmount() > 0);
		Assert.isTrue(Utiles.checkCreditCard(payment.getCreditCard())
				|| Utiles.checkEmptyCreditCard(payment.getCreditCard()));
		Appointment appointment = payment.getAppointment();

		if (Utiles.checkEmptyCreditCard(payment.getCreditCard())) {
			payment.setCreditCard(null);
		}

		payment = paymentRepository.save(payment);
		appointment.setPayment(payment);
		return payment;
	}

	public void delete(Payment payment) {
		paymentRepository.delete(payment);
	}

	// Other business methods -------------------------------------------------
}
