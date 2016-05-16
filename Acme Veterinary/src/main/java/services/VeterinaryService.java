package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.VeterinaryRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Veterinary;
import forms.VeterinaryForm;

@Service
@Transactional
public class VeterinaryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private VeterinaryRepository veterinaryRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public VeterinaryService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Veterinary create() {
		Veterinary result;
		result = new Veterinary();
		return result;
	}

	public Veterinary findOne(int veterinaryId) {
		Veterinary result;
		result = veterinaryRepository.findOne(veterinaryId);
		return result;
	}

	public Collection<Veterinary> findAll() {
		Collection<Veterinary> result;
		result = veterinaryRepository.findAll();
		return result;
	}

	public Veterinary save(Veterinary veterinary) {
		Assert.notNull(veterinary);
		return veterinaryRepository.save(veterinary);
	}

	// Other business methods -------------------------------------------------

	public Veterinary findByPrincipal() {
		Veterinary result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		result = veterinaryRepository.findByPrincipal(userAccount.getId());
		Assert.notNull(result);
		return result;
	}

	public void save(VeterinaryForm veterinaryForm) {
		Assert.notNull(veterinaryForm);
		Assert.isTrue(
				veterinaryForm.getPassword().equals(
						veterinaryForm.getSecondPassword()),
				"Las passwords no coinciden");

		Veterinary veterinary;
		veterinary = create();

		UserAccount uA = new UserAccount();
		uA.setUsername(veterinaryForm.getUsername());

		Authority authority = new Authority();
		authority.setAuthority("VETERINARY");
		Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		uA.setAuthorities(authorities);
		veterinary.setUserAccount(uA);

		String password = veterinaryForm.getPassword();
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String md5 = encoder.encodePassword(password, null);
		uA.setPassword(md5);

		veterinary.setName(veterinaryForm.getName());
		veterinary.setSurname(veterinaryForm.getSurname());
		veterinary.setPhone(veterinaryForm.getPhone());
		veterinary.setEmail(veterinaryForm.getEmail());
		veterinary.setClinic(veterinaryForm.getClinic());

		save(veterinary);
	}
	
	// Devuelve lista de veterinarios asignados a una clinica
	public Collection<Veterinary> findByClinic(int clinicId) {
		Collection<Veterinary> result;
		result = veterinaryRepository.findByClinic(clinicId);
		return result;
	}
}
