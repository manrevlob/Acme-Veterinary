package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PetTypeRepository;
import domain.PetType;

@Service
@Transactional
public class PetTypeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PetTypeRepository petTypeRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------

	public PetTypeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public PetType create() {
		Assert.isTrue(actorService.isAdministrator());
		PetType result;
		result = new PetType();
		return result;
	}

	public PetType findOne(int petTypeId) {
		PetType result;
		result = petTypeRepository.findOne(petTypeId);
		return result;
	}

	public Collection<PetType> findAll() {
		Collection<PetType> result;
		result = petTypeRepository.findAll();
		return result;
	}

	public PetType save(PetType petType) {
		Assert.isTrue(actorService.isAdministrator());
		Assert.isTrue(checkName(petType));
		Assert.notNull(petType);
		return petTypeRepository.save(petType);
	}
	
	// Other business methods -------------------------------------------------
	
	public boolean checkName(PetType petType){
		Assert.notNull(petType);
		boolean result = true;
		Collection<PetType> petTypes; 
		petTypes = findAll();
		for (PetType p: petTypes){
			if (p.getName().compareTo(petType.getName()) == 0){
				result = false;
				break;
			}
		}
		
		return result;
	}
}
