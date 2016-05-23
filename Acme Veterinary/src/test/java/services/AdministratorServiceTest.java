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
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class AdministratorServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private AdministratorService administratorService;

	// Tests -------------------------------------------------

	// Probamos que se devuelva correctamente el administrator logueado
	@Test
	public void testFindByPrincipal() {
		Administrator administrator;
		authenticate("admin");
		administrator = administratorService.findByPrincipal();
		Assert.notNull(administrator);
		authenticate(null);
	}

	// Test Negativos ----------------------------------------

	// Comprobamos que el método falla al no estar logueado
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void testFindByPrincipalNegative() {
		unauthenticate();
		administratorService.findByPrincipal();
	}

	// Comprobamos que el método falla al estar logueado con otro rol
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void testFindByPrincipalNegative2() {
		authenticate("customer2");
		administratorService.findByPrincipal();
		unauthenticate();
	}

}