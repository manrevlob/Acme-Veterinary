package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customer;

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
	public void testCreateCustomer(){
		
		Customer customer;
		customer = customerService.create();
		customer.setName("TEST");
		customer.setSurname("TEST");
		customer.setPhone("123456789");
		customer.setEmail("email@email.com");
		customerService.save(customer);
		
		Assert.notNull(customerService.findOne(customer.getId()));
		
	}
	
	

	



}