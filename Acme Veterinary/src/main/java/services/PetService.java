package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PetRepository;
import domain.Pet;

@Service
@Transactional
public class PetService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PetRepository petRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public PetService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Pet create() {
		Pet result;
		result = new Pet();
		return result;
	}

	public Pet findOne(int petId) {
		Pet result;
		result = petRepository.findOne(petId);
		return result;
	}

	public Collection<Pet> findAll() {
		Collection<Pet> result;
		result = petRepository.findAll();
		return result;
	}

	public Pet save(Pet pet) {
		Assert.notNull(pet);
		return petRepository.save(pet);
	}
	
	public void delete(Pet pet) {
		petRepository.delete(pet);
	}
	
	// Other business methods -------------------------------------------------
}
