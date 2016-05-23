package services;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ClinicServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private ClinicService clinicService;

	// Tests -------------------------------------------------

	// Comprobamos que un administrador puede crear una nueva cl�nica
	@Test
	public void testCreateClinic() {
		authenticate("admin");
		Clinic clinic;
		clinic = clinicService.create();
		clinic.setAddress("TEST");
		clinic.setName("TEST");
		clinic.setZipCode("41006");
		clinicService.save(clinic);
		Assert.notNull(clinic.getId());
		unauthenticate();

	}
	
	// Comprobamos que un administrador puede borrar una cl�nica
	@Test
	public void testDeleteClinic() {
		authenticate("admin");
		Clinic clinic;
		clinic = clinicService.findOne(45);
		clinicService.delete(clinic);
		Assert.isTrue(clinic.getIsDeleted() == true);
		unauthenticate();

	}

	// Test Negativos ----------------------------------------

	// Comprobamos que un usuario que no es administrador no puede crear una cl�nica

	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void testCustomerCreateClinicNegative() {

		authenticate("customer1");
		Clinic clinic;
		clinic = clinicService.create();
		clinic.setAddress("TEST");
		clinic.setName("TEST");
		clinic.setZipCode("41006");
		clinicService.save(clinic);
		unauthenticate();

	}

	// Comprobamos que un administrador no puede crear una cl�nica si deja alg�n campo sin rellenar

	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void testCreateClinicNegative() {

		authenticate("admin");
		Clinic clinic;
		clinic = clinicService.create();
		clinic.setName("TEST");
		clinic.setZipCode("41006");
		clinicService.save(clinic);
		unauthenticate();

	}

}