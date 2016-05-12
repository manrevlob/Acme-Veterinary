package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HistoryRepository;
import domain.History;

@Service
@Transactional
public class HistoryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private HistoryRepository historyRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	@Autowired
	private PetService petService;
	@Autowired
	private CustomerService customerService;
	
	// Constructors -----------------------------------------------------------

	public HistoryService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public History create() {
		History result;
		result = new History();
		return result;
	}

	public History findOne(int historyId) {
		History result;
		result = historyRepository.findOne(historyId);
		return result;
	}

	public Collection<History> findAll() {
		Collection<History> result;
		result = historyRepository.findAll();
		return result;
	}

	public History save(History history) {
		Assert.notNull(history);
		return historyRepository.save(history);
	}

	public void delete(History history) {
		historyRepository.delete(history);
	}

	// Other business methods -------------------------------------------------

	public Collection<History> findAllByPet(int petId) {
		Assert.isTrue(actorService.isCustomer());
		Assert.isTrue(customerService.findByPrincipal().equals(petService.findOne(petId).getCustomer()));
		Collection<History> result;
		result = historyRepository.findAllByPet(petId);
		return result;
	}
}
