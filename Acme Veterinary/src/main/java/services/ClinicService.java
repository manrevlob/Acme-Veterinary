package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ClinicRepository;
import domain.Clinic;

@Service
@Transactional
public class ClinicService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ClinicRepository clinicRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ClinicService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Clinic create() {
		Clinic result;
		result = new Clinic();
		return result;
	}

	public Clinic findOne(int clinicId) {
		Clinic result;
		result = clinicRepository.findOne(clinicId);
		return result;
	}

	public Collection<Clinic> findAll() {
		Collection<Clinic> result;
		result = clinicRepository.findAll();
		return result;
	}

	public Clinic save(Clinic clinic) {
		Assert.notNull(clinic);
		return clinicRepository.save(clinic);
	}
	
	public void delete(Clinic clinic) {
		clinicRepository.delete(clinic);
	}
	
	// Other business methods -------------------------------------------------
}
