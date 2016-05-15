package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PetRepository;
import domain.Customer;
import domain.Pet;

@Service
@Transactional
public class PetService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PetRepository petRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	@Autowired
	private CustomerService customerService;

	// Constructors -----------------------------------------------------------

	public PetService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Pet create() {
		Pet result;
		result = new Pet();
		Customer customer = customerService.findByPrincipal();
		result.setCustomer(customer);
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

	public void delete(int petId) {
		Assert.isTrue(actorService.isCustomer());
		Pet pet = findOne(petId);
		pet.setIsDeleted(true);
		petRepository.save(pet);
	}

	// Other business methods -------------------------------------------------

	public Collection<Pet> findAllOwner() {
		Assert.isTrue(actorService.isCustomer());
		Collection<Pet> result;
		Customer customer;
		customer = customerService.findByPrincipal();
		result = petRepository.findAllOwner(customer);
		return result;
	}

	public Collection<Pet> findAllByCustomer(int customerId) {
		Assert.isTrue(actorService.isVeterinary());
		Collection<Pet> result;
		Customer customer;
		customer = customerService.findOne(customerId);
		result = petRepository.findAllOwner(customer);
		return result;
	}
}
