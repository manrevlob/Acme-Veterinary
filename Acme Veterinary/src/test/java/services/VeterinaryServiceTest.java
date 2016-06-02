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
import domain.Clinic;
import domain.Veterinary;
import forms.VeterinaryForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class VeterinaryServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private VeterinaryService veterinaryService;
	@Autowired
	private ClinicService clinicService;

	// Tests -------------------------------------------------

	// Comprobamos que podemos registrar en el sistema un veterinary como
	// administrador
	@Test
	public void testRegisterVeterinary() {
		Collection<Veterinary> before;
		Collection<Veterinary> after;
		VeterinaryForm veterinaryForm;
		Clinic clinic;

		authenticate("admin");

		before = veterinaryService.findAll();

		// Obtenemos la Id de la clinic1
		int clinicId = 50;
		clinic = clinicService.findOne(clinicId);

		veterinaryForm = new VeterinaryForm();
		veterinaryForm.setClinic(clinic);
		veterinaryForm.setEmail("TEST@acme.com");
		veterinaryForm.setName("TEST");
		veterinaryForm.setPassword("TESTT");
		veterinaryForm.setPhone("789456123");
		veterinaryForm.setSecondPassword("TESTT");
		veterinaryForm.setSurname("TEST");
		veterinaryForm.setUsername("TESTT");
		veterinaryService.save(veterinaryForm);

		after = veterinaryService.findAll();

		Assert.isTrue(before.size() < after.size());

		unauthenticate();
	}

	// Test Negativos ----------------------------------------

	// Comprobamos que no podemos registrar en el sistema un veterinario con un
	// rol distinto a administrador
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterVeterinaryNeg() {
		VeterinaryForm veterinaryForm;
		Clinic clinic;

		authenticate("customer1");

		// Obtenemos la Id de la clinic1
		int clinicId = 50;
		clinic = clinicService.findOne(clinicId);

		veterinaryForm = new VeterinaryForm();
		veterinaryForm.setClinic(clinic);
		veterinaryForm.setEmail("TEST@acme.com");
		veterinaryForm.setName("TEST");
		veterinaryForm.setPassword("TESTT");
		veterinaryForm.setPhone("789456123");
		veterinaryForm.setSecondPassword("TESTT");
		veterinaryForm.setSurname("TEST");
		veterinaryForm.setUsername("TESTT");
		veterinaryService.save(veterinaryForm);

		unauthenticate();
	}
	// Comprobamos que no podemos registrar en el sistema un veterinario con las
	// contraseñas distintas
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterVeterinaryNeg2() {
		VeterinaryForm veterinaryForm;
		Clinic clinic;

		authenticate("admin");

		// Obtenemos la Id de la clinic1
		int clinicId = 50;
		clinic = clinicService.findOne(clinicId);

		veterinaryForm = new VeterinaryForm();
		veterinaryForm.setClinic(clinic);
		veterinaryForm.setEmail("TEST@acme.com");
		veterinaryForm.setName("TEST");
		veterinaryForm.setPassword("TESTT");
		veterinaryForm.setPhone("789456123");
		veterinaryForm.setSecondPassword("TESTTTTT");
		veterinaryForm.setSurname("TEST");
		veterinaryForm.setUsername("TESTT");
		veterinaryService.save(veterinaryForm);

		unauthenticate();
	}

}