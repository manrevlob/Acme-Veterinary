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
import domain.Customer;
import forms.CustomerForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CustomerServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private CustomerService customerService;


	// Tests -------------------------------------------------

	// Nos registramos en el sistema como nuevo customer
	@Test
	public void testCreateCustomer() {

		Customer customer;
		customer = customerService.create();
		customer.setName("TEST");
		customer.setSurname("TEST");
		customer.setPhone("123456789");
		customer.setEmail("email@email.com");
		customer = customerService.save(customer);

		Assert.notNull(customerService.findOne(customer.getId()));

	}

	// Comprobamos que un usuario puede cambiar su informacíón personal

	@Test
	public void testChangePersonalInformation() {
		authenticate("customer1");

		Customer customer;
		// Buscamos el customer principal
		customer = customerService.findByPrincipal();
		// Modificamos el nombre del usuario logueado
		customer.setName("TEST");
		customerService.save(customer);
		// Obtenemos el nombre nuevo
		String nameAfter = customer.getName();

		// Comprobamos que no son el mismo
		Assert.isTrue(nameAfter == "TEST");

		unauthenticate();

	}

	// Test Negativos ----------------------------------------

	//Comprobamos que no se crea el usuario si las contraseñas no coinciden
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void testCreateCustomerEmailNegative() {
		CustomerForm customerForm;
		customerForm = new CustomerForm();
		customerForm.setEmail("email@test.com");
		customerForm.setName("TEST");
		customerForm.setPhone("123456789");
		customerForm.setSurname("TEST");
		customerForm.setPassword("prueba");
		customerForm.setSecondPassword("nocoincide");
		customerForm.setUsername("prueba");
		customerService.save(customerForm);

	}

	// Comprobamos que un usuario no puede modificar su informacíón personal dejando campos vacíos 
	 
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void testChangePersonalInformationNegative() {

		authenticate("customer1");

		Customer customer;
		// Buscamos el customer principal
		customer = customerService.findByPrincipal();
		// Modificamos el nombre del usuario logueado
		customer.setName("");
		customerService.save(customer);


		unauthenticate();

	}
}