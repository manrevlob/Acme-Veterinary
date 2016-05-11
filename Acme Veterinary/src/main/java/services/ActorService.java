package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.Veterinary;
import forms.PassForm;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository actorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CustomerService customerService;
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private VeterinaryService veterinaryService;

	// Constructors -----------------------------------------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Actor findOne(int actorId) {
		Actor result;
		result = actorRepository.findOne(actorId);
		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;
		result = actorRepository.findAll();
		return result;
	}

	public Actor save(Actor actor) {
		Assert.notNull(actor);
		return actorRepository.save(actor);
	}

	// Other business methods -------------------------------------------------

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		result = actorRepository.findByPrincipal(userAccount.getId());
		Assert.notNull(result);
		return result;
	}

	private boolean checkRole(String role) {
		boolean result;
		Collection<Authority> authorities;
		result = false;
		authorities = LoginService.getPrincipal().getAuthorities();
		for (Authority a : authorities) {
			result = result || a.getAuthority().equals(role);
		}
		return result;
	}

	public boolean isAuthenticated() {
		boolean result = false;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		if (userAccount != null) {
			result = true;
		}
		return result;
	}

	public boolean isAdministrator() {
		boolean result;
		result = checkRole(Authority.ADMIN);
		return result;
	}

	public boolean isCustomer() {
		boolean result;
		result = checkRole(Authority.CUSTOMER);
		return result;
	}

	public boolean isVeterinary() {
		boolean result;
		result = checkRole(Authority.VETERINARY);
		return result;
	}

	public Collection<Actor> findAllExceptMe() {
		Collection<Actor> result;
		Assert.isTrue(findByPrincipal() != null);
		result = actorRepository.findAll();
		result.remove(findByPrincipal());
		return result;
	}

	public Customer setCustomer(Actor actor) {
		Customer customer;
		customer = customerService.findOne(actor.getId());
		customer.setName(actor.getName());
		customer.setSurname(actor.getSurname());
		customer.setPhone(actor.getPhone());
		customer.setEmail(actor.getEmail());

		return customer;

	}

	public Administrator setAdministrator(Actor actor) {
		Administrator administrator;
		administrator = administratorService.findOne(actor.getId());
		administrator.setName(actor.getName());
		administrator.setSurname(actor.getSurname());
		administrator.setPhone(actor.getPhone());
		administrator.setEmail(actor.getEmail());

		return administrator;
	}

	public Veterinary setVeterinary(Actor actor) {
		Veterinary veterinary;
		veterinary = veterinaryService.findOne(actor.getId());
		veterinary.setName(actor.getName());
		veterinary.setSurname(actor.getSurname());
		veterinary.setPhone(actor.getPhone());
		veterinary.setEmail(actor.getEmail());

		return veterinary;
	}

	// Profile

	public void changeProfile(Actor actor) {

		if (isAdministrator()) {
			Administrator administrator = setAdministrator(actor);
			administratorService.save(administrator);
		} else if (isCustomer()) {
			Customer customer = setCustomer(actor);
			customerService.save(customer);
		} else if (isVeterinary()) {
			Veterinary veterinary = setVeterinary(actor);
			veterinaryService.save(veterinary);
		}
	}
	
	// Change password
	
	public void changePassword(PassForm passForm) {
		Assert.isTrue(isAuthenticated());
		Assert.isTrue(checkPassword(passForm));
		Actor actor;
		actor = findByPrincipal();
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String passNew = passForm.getPassNew();
		String hashNew = encoder.encodePassword(passNew, null);
		actor.getUserAccount().setPassword(hashNew);
		
		save(actor);
	}
	
	public boolean checkPassword(PassForm passForm){
		boolean result = false;
		Actor actor;
		actor = findByPrincipal();
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String passOld = passForm.getPassLast();
		String hashOld = encoder.encodePassword(passOld, null);
		
		if (hashOld.equals(actor.getUserAccount().getPassword())){
			if (passForm.getPassNew().equals(passForm.getPassConf())){
				result = true;
			}
		}
		
		return result;
	}

}
