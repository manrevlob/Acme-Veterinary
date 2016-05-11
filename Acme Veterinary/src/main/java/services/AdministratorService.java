package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AdministratorRepository administratorRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Administrator create() {
		Administrator result;
		result = new Administrator();
		return result;
	}

	public Administrator findOne(int administratorId) {
		Administrator result;
		result = administratorRepository.findOne(administratorId);
		return result;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;
		result = administratorRepository.findAll();
		return result;
	}

	public Administrator save(Administrator administrator) {
		Assert.notNull(administrator);
		return administratorRepository.save(administrator);
	}

	// Other business methods -------------------------------------------------

	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount = LoginService.getPrincipal();
		result = administratorRepository.findByPrincipal(userAccount.getId());
		Assert.notNull(result);
		return result;
	}
}
