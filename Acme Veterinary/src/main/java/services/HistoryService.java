package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HistoryRepository;
import domain.History;
import domain.Treatment;
import forms.HistoryForm;

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
		Assert.isTrue(actorService.isVeterinary());
		History result;
		result = new History();
		result.setMoment(new Date(System.currentTimeMillis() - 1000));
		return result;
	}

	public History findOne(int historyId) {
		Assert.isTrue(actorService.isVeterinary() || actorService.isCustomer());
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
		Assert.isTrue(actorService.isVeterinary());
		Assert.notNull(history);
		return historyRepository.save(history);
	}

	public void delete(History history) {
		historyRepository.delete(history);
	}

	// Other business methods -------------------------------------------------

	public Collection<History> findAllByPet(int petId) {
		Assert.isTrue(actorService.isCustomer() || actorService.isVeterinary());
		if(actorService.isCustomer()){
			Assert.isTrue(customerService.findByPrincipal().equals(petService.findOne(petId).getCustomer()) && petService.isOwner(petId));
		}
		Collection<History> result;
		result = historyRepository.findAllByPet(petId);
		return result;
	}

	public void convertFormToHistory(HistoryForm historyForm) {
		Assert.isTrue(actorService.isVeterinary());
		Assert.isTrue(checkDates(historyForm));
		History history;

		history = create();
		
		history.setDiagnosis(historyForm.getDiagnosis());
		history.setAppointment(historyForm.getAppointment());
		history.setPet(historyForm.getAppointment().getPet());
		history.setVeterinary(historyForm.getAppointment().getVeterinary());
		
		if(checkTreatment(historyForm)){
			Treatment treatment;
			treatment = new Treatment();
			
			treatment.setDescription(historyForm.getTreatmentDescription());
			treatment.setStartMoment(historyForm.getTreatmentStartMoment());
			treatment.setEndMoment(historyForm.getTreatmentEndMoment());
			
			history.setTreatment(treatment);
		}
		
		history = save(history);
		
		history.getAppointment().setHistory(history);
		
	}
	
	public boolean checkTreatment(HistoryForm historyForm) {
		Assert.notNull(historyForm);
		boolean result = true;

		if (historyForm.getTreatmentDescription().isEmpty()
				|| historyForm.getTreatmentStartMoment().equals(null)
				|| historyForm.getTreatmentEndMoment().equals(null)) {
			result = false;
		}

		return result;
	}
	
	public boolean checkDates(HistoryForm historyForm){
		boolean result = false;
		Calendar fechaActual = Calendar.getInstance();
		Calendar startMoment = Calendar.getInstance(), endMoment = Calendar.getInstance();
		startMoment.setTime(historyForm.getTreatmentStartMoment());
		endMoment.setTime(historyForm.getTreatmentEndMoment());
		if (fechaActual.before(startMoment)){
			if (startMoment.before(endMoment)){
				result = true;
			}
		}
		
		return result;
	}
}
