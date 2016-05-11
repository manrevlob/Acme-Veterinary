package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CustomerRepository customerRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CustomerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Customer create() {
		Customer result;
		result = new Customer();
		return result;
	}

	public Customer findOne(int customerId) {
		Customer result;
		result = customerRepository.findOne(customerId);
		return result;
	}

	public Collection<Customer> findAll() {
		Collection<Customer> result;
		result = customerRepository.findAll();
		return result;
	}

	public Customer save(Customer customer) {
		Assert.notNull(customer);
		return customerRepository.save(customer);
	}
	
	// Other business methods -------------------------------------------------
}
