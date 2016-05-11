package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.VeterinaryRepository;
import domain.Veterinary;

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
}
