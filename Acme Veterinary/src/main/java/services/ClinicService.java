package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ClinicRepository;
import domain.Actor;
import domain.Appointment;
import domain.Clinic;
import domain.Customer;
import domain.Message;
import domain.Veterinary;

@Service
@Transactional
public class ClinicService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ClinicRepository clinicRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private MessageService messageService;
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
		clinic.setIsDeleted(true);
	}
	
	// Other business methods -------------------------------------------------
	
	public Collection<Clinic> findAllNotDeleted(){
		Collection<Clinic> clinics;
		clinics = clinicRepository.findAllNotDeleted();
		return clinics;
	}
	
	public void deleteClinic(int clinicId){
		Assert.isTrue(actorService.isAdministrator());
		Clinic clinic = findOne(clinicId);
		sendMessageToCustomer(clinicId);
		delete(clinic);		
	}

	public void sendMessageToCustomer(int clinicId){	
		Actor admin = actorService.findByPrincipal();
		Clinic clinic = findOne(clinicId);
		Collection<Veterinary> veterinaries;
		Collection<Appointment> appointments;
		veterinaries = clinic.getVeterinaries();
		Customer customer;
		
		for(Veterinary v : veterinaries){
			appointments = appointmentService.findAllByVeterinaryNoExpired(v);
			for(Appointment a : appointments){
				customer = a.getPet().getCustomer();
				Message message = messageService.create(admin,customer,"APPOINTMENT CANCEL", "Clinic "+ clinic.getName()+" has closed. Your appointment "+ a.getDay().toString()+" has been cancelled. Sorry about that.");
				messageService.sendMessage(message);
			}
			
		}
		
		
		
	}
}
