package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import domain.MessageFolder;
import forms.CustomerForm;

@Service
@Transactional
public class CustomerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CustomerRepository customerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private MessageFolderService messageFolderService;
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------

	public CustomerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Customer create() {
		Customer result;
		UserAccount userAccount;
		Authority authority;
		Collection<Authority> authorities;
		MessageFolder inbox;
		MessageFolder outbox;
		MessageFolder trashbox;
		MessageFolder spambox;
		Collection<MessageFolder> messageFolders;

		userAccount = new UserAccount();
		result = new Customer();
		authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		authorities = new ArrayList<Authority>();
		messageFolders = new ArrayList<MessageFolder>();

		inbox = messageFolderService.create();
		outbox = messageFolderService.create();
		trashbox = messageFolderService.create();
		spambox = messageFolderService.create();

		inbox.setSystem(true);
		outbox.setSystem(true);
		trashbox.setSystem(true);
		spambox.setSystem(true);

		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);

		inbox.setName("In box");
		outbox.setName("Out box");
		trashbox.setName("Trash box");
		spambox.setName("Spam box");

		messageFolders.add(inbox);
		messageFolders.add(outbox);
		messageFolders.add(trashbox);
		messageFolders.add(spambox);

		messageFolders = messageFolderService.saveAll(messageFolders);
		result.setMessageFolders(messageFolders);

		result.setRegisterMoment(new Date(System.currentTimeMillis() - 1000));

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
		Assert.isTrue(StringUtils.isNotEmpty(customer.getName()),
				"Name is null");
		Assert.isTrue(StringUtils.isNotEmpty(customer.getSurname()),
				"Surname is null");
		return customerRepository.save(customer);
	}

	// Other business methods -------------------------------------------------

	public Customer findByPrincipal() {
		Customer result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		result = customerRepository.findByPrincipal(userAccount.getId());
		Assert.notNull(result);
		return result;
	}

	public void save(CustomerForm customerForm) {
		Assert.notNull(customerForm);
		Assert.isTrue(
				customerForm.getPassword().equals(
						customerForm.getSecondPassword()),
				"Las passwords no coinciden");

		Customer customer;
		customer = create();

		UserAccount uA = new UserAccount();
		uA.setUsername(customerForm.getUsername());

		Authority authority = new Authority();
		authority.setAuthority("CUSTOMER");
		Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		uA.setAuthorities(authorities);
		customer.setUserAccount(uA);

		String password = customerForm.getPassword();
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String md5 = encoder.encodePassword(password, null);
		uA.setPassword(md5);

		customer.setName(customerForm.getName());
		customer.setSurname(customerForm.getSurname());
		customer.setPhone(customerForm.getPhone());
		customer.setEmail(customerForm.getEmail());

		save(customer);
	}

	public Collection<Customer> findByKeyword(String keyword) {
		Assert.isTrue(actorService.isVeterinary());
		Collection<Customer> result;
		result = customerRepository.findByKeyword(keyword);
		return result;
	}

	// Dashboard
	public Integer numberNewCustomers() {
		Assert.isTrue(actorService.isAdministrator());
		Integer result = 0;
		result = customerRepository.numberNewCustomers();
		return result;
	}

	public Integer numberCustomers() {
		Assert.isTrue(actorService.isAdministrator());
		Integer result = 0;
		result = findAll().size();
		return result;
	}

	public Collection<Customer> customerMorePay() {
		Assert.isTrue(actorService.isAdministrator());
		Collection<Customer> result;
		result = customerRepository.customerMorePay();
		return result;
	}
	
	
	
}
