package services;

import java.sql.Date;
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
import domain.Order;
import domain.Voucher;
import forms.VoucherForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class VoucherServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private VoucherService voucherService;
	@Autowired
	private OrderService orderService;

	// Tests -------------------------------------------------

	// Comprobamos que podemos un cupon como administrador
	@Test
	public void testCreateAndSave() {
		Collection<Voucher> before;
		Collection<Voucher> after;

		authenticate("admin");

		before = voucherService.findAll();

		Voucher voucher;
		voucher = voucherService.create();
		voucher.setCode("TEST");
		voucher.setMinimumOrder(10.0);
		voucher.setValidUntil(new Date(12 / 13 / 2016));
		voucher.setValue(20.0);
		voucherService.save(voucher);

		after = voucherService.findAll();

		Assert.isTrue(before.size() < after.size());

		unauthenticate();
	}

	// Comprobamos que podemos aplicar un cupon como customer
	@Test
	public void testApplyVoucher() {
		Double before;
		Double after;
		VoucherForm voucherForm;
		Order order;

		authenticate("customer1");

		// Obtenemos la Id de la order1
		int orderId = 77;
		order = orderService.findOne(orderId);

		before = order.getTotalPrice().getAmount();

		voucherForm = new VoucherForm();
		voucherForm.setCanceled(order.getIsCanceled());
		voucherForm.setCode("WINTER");
		voucherForm.setCustomer(order.getCustomer());
		voucherForm.setMoment(order.getMoment());
		voucherForm.setTicker(order.getTicker());
		voucherForm.setTotalPrice(order.getTotalPrice());
		voucherForm = voucherService.applyVoucher(voucherForm);

		after = voucherForm.getTotalPrice().getAmount();

		Assert.isTrue(before > after);

		unauthenticate();
	}

	// Test Negativos ----------------------------------------

	// Comprobamos que no podemos crear un cupon con un rol distinto a
	// administrador
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNeg() {
		authenticate("customer1");

		Voucher voucher;
		voucher = voucherService.create();
		voucher.setCode("TEST");
		voucher.setMinimumOrder(10.0);
		voucher.setValidUntil(new Date(12 / 13 / 2016));
		voucher.setValue(20.0);
		voucherService.save(voucher);

		unauthenticate();
	}

	// Comprobamos que no podemos aplicar un cupon en un pedido con un rol
	// distinto a customer
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testApplyVoucherNeg() {
		VoucherForm voucherForm;
		Order order;

		authenticate("admin");

		// Obtenemos la Id de la order1
		int orderId = 77;
		order = orderService.findOne(orderId);

		voucherForm = new VoucherForm();
		voucherForm.setCanceled(order.getIsCanceled());
		voucherForm.setCode("WINTER");
		voucherForm.setCustomer(order.getCustomer());
		voucherForm.setMoment(order.getMoment());
		voucherForm.setTicker(order.getTicker());
		voucherForm.setTotalPrice(order.getTotalPrice());
		voucherForm = voucherService.applyVoucher(voucherForm);

		unauthenticate();
	}

	// Comprobamos que no podemos aplicar un cupon ya usado

	// Comprobamos que no podemos aplicar un cupon que no existe
}