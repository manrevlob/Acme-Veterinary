package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Appointment;
import domain.CreditCard;
import domain.Payment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class PaymentServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private PaymentService paymentService;
	@Autowired
	private AppointmentService appointmentService;

	// Tests -------------------------------------------------

	// Comprobamos que podemos crear un pago como veterinario con tarjeta de
	// credito
	@Test
	public void testCreateAndSave() {
		Collection<Payment> before;
		Collection<Payment> after;
		Appointment appointment;
		CreditCard creditCard;

		authenticate("veterinary1");

		// Obtenemos la Id del appointment1
		int appointmentId = 66;
		appointment = appointmentService.findOne(appointmentId);

		before = paymentService.findAll();

		Payment payment;
		payment = paymentService.create(appointment);
		payment.getTotalCost().setAmount(5.5);
		// Create CreditCard
		creditCard = new CreditCard();
		creditCard.setBrand("test");
		creditCard.setCvv(111);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2020);
		creditCard.setName("test");
		creditCard.setNumber("5207537760582875");
		payment.setCreditCard(creditCard);
		paymentService.save(payment);

		after = paymentService.findAll();

		Assert.isTrue(before.size() < after.size());

		unauthenticate();
	}

	// Comprobamos que podemos crear un pago como veterinario sin tarjeta de
	// credito
	@Test
	public void testCreateAndSave2() {
		Collection<Payment> before;
		Collection<Payment> after;
		Appointment appointment;

		authenticate("veterinary1");

		// Obtenemos la Id del appointment1
		int appointmentId = 66;
		appointment = appointmentService.findOne(appointmentId);

		before = paymentService.findAll();

		Payment payment;
		payment = paymentService.create(appointment);
		payment.getTotalCost().setAmount(5.5);
		// Sin tarjeta de credito
		CreditCard creditCard = new CreditCard();
		creditCard.setBrand("");
		creditCard.setCvv(null);
		creditCard.setExpirationMonth(0);
		creditCard.setExpirationYear(0);
		creditCard.setName("");
		creditCard.setNumber("");
		payment.setCreditCard(creditCard);
		paymentService.save(payment);

		after = paymentService.findAll();

		Assert.isTrue(before.size() < after.size());

		unauthenticate();
	}

	// Test Negativos ----------------------------------------

	// Comprobamos que no podemos crear un pago como veterinario sin la cantidad
	// de dinero
	@Rollback(true)
	@Test(expected = NullPointerException.class)
	public void testCreateAndSaveNeg() {
		Appointment appointment;

		authenticate("veterinary1");

		// Obtenemos la Id del appointment1
		int appointmentId = 66;
		appointment = appointmentService.findOne(appointmentId);

		Payment payment;
		payment = paymentService.create(appointment);
		// Sin cantidad de dinero
		// Sin tarjeta de credito
		paymentService.save(payment);

		unauthenticate();
	}

	// Comprobamos que no podemos crear un pago como veterinario con una cita ya
	// pagada
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNeg2() {
		Appointment appointment;

		authenticate("veterinary1");

		// Obtenemos la Id del appointment1
		int appointmentId = 66;
		appointment = appointmentService.findOne(appointmentId);

		Payment payment1;
		payment1 = paymentService.create(appointment);
		payment1.getTotalCost().setAmount(5.5);
		// Sin tarjeta de credito
		paymentService.save(payment1);

		// Volvemos a hacer el pago de la misma cita
		Payment payment2;
		payment2 = paymentService.create(appointment);
		payment2.getTotalCost().setAmount(5.5);
		// Sin tarjeta de credito
		paymentService.save(payment2);

		unauthenticate();
	}

	// Comprobamos que no podemos crear un pago con un distinto a veterinario
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNe3() {
		Appointment appointment;

		authenticate("admin");

		// Obtenemos la Id del appointment1
		int appointmentId = 66;
		appointment = appointmentService.findOne(appointmentId);

		Payment payment;
		payment = paymentService.create(appointment);
		payment.getTotalCost().setAmount(5.5);
		// Sin tarjeta de credito
		paymentService.save(payment);

		unauthenticate();
	}

	// Comprobamos que no podemos crear un pago como veterinario con un error en
	// la tarjeta de credito
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNeg4() {
		Appointment appointment;
		CreditCard creditCard;

		authenticate("veterinary1");

		// Obtenemos la Id del appointment1
		int appointmentId = 66;
		appointment = appointmentService.findOne(appointmentId);

		Payment payment;
		payment = paymentService.create(appointment);
		payment.getTotalCost().setAmount(5.5);
		// Create CreditCard
		creditCard = new CreditCard();
		creditCard.setBrand("test");
		creditCard.setCvv(111);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2020);
		creditCard.setName("test");
		creditCard.setNumber("5207537760555575");
		payment.setCreditCard(creditCard);
		paymentService.save(payment);

		unauthenticate();
	}

}