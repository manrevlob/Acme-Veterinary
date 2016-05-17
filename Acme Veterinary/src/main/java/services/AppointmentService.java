package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AppointmentRepository;
import domain.Appointment;
import domain.Customer;
import domain.Message;
import domain.Veterinary;
import forms.AppointmentForm;

@Service
@Transactional
public class AppointmentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AppointmentRepository appointmentRepository;	

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	@Autowired
	private VeterinaryService veterinaryService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private MessageService messageService;
	
	// Constructors -----------------------------------------------------------

	public AppointmentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Appointment create() {
		Appointment result;
		result = new Appointment();
		return result;
	}

	public Appointment findOne(int appointmentId) {
		Appointment result;
		result = appointmentRepository.findOne(appointmentId);
		return result;
	}

	public Collection<Appointment> findAll() {
		Collection<Appointment> result;
		result = appointmentRepository.findAll();
		return result;
	}

	public Appointment save(Appointment appointment) {
		Assert.notNull(appointment);
		return appointmentRepository.save(appointment);
	}
	
	public void delete(Appointment appointment) {
		appointmentRepository.delete(appointment);
	}
	
	// Other business methods -------------------------------------------------
	//Devuelve todas las citas de veterinario principal
	public Collection<Appointment> findAllOwn() {
		Assert.isTrue(actorService.isVeterinary());
		Collection<Appointment> result;
		Veterinary veterinary;
		veterinary = veterinaryService.findByPrincipal();
		result = appointmentRepository.findAllOwn(veterinary);
		return result;
	}
	//Devuelve todas las citas de veterinario principal NO EXPIRADAS y ordenadas por el dia y la hora
	public Collection<Appointment> findAllOwnNoExpired() {
		Assert.isTrue(actorService.isVeterinary());
		Collection<Appointment> result;
		Veterinary veterinary;
		veterinary = veterinaryService.findByPrincipal();
		result = appointmentRepository.findAllOwnNoExpired(veterinary);
		return result;
	}
	
	//Este metodo se podr� utilizar sin estar logueado, x eso no lleva asserts
	public boolean getVeterinaryisBooked(String day, String startTime,	String endTime, Veterinary veterinary) {
		boolean res = false;
		@SuppressWarnings("deprecation")
		Date dayDate = new Date(day);
		Collection<Appointment> appointments = appointmentRepository.getVeterinaryIsBooked(dayDate, startTime, endTime, veterinary);
		if ((appointments != null) && (appointments.size() != 0))
			res = true;
		return res;
	}
	
	public LinkedHashMap<String, String> getNextDays() {
		LinkedHashMap<String, String> days = new LinkedHashMap<String, String>();
		Calendar c = Calendar.getInstance();
		String day;
		Date dt = c.getTime();
		DateFormat ndf = new SimpleDateFormat("dd/MM/yyyy");

		for (int i = 0; i < 7; i++) {
			day = ndf.format(dt);
			days.put(day, day);
			c.add(Calendar.DAY_OF_MONTH, 1);
			dt = c.getTime();
		}
		return days;
	}
	
	public String getDay() {
		Calendar c = Calendar.getInstance();
		String day;
		Date dt = c.getTime();
		DateFormat ndf = new SimpleDateFormat("dd/MM/yyyy");
		day = ndf.format(dt);
		return day;
	}
	
	public Appointment reconstruct(AppointmentForm appointmentForm) {
		Assert.isTrue(actorService.isCustomer());
		Appointment appointment= new Appointment();
		@SuppressWarnings("deprecation")
		Date day = new Date(appointmentForm.getStartMoment());
		appointment.setDay(day);
		appointment.setStartTime(appointmentForm.getStartTime());
		appointment.setEndTime(appointmentForm.getEndTime());
		appointment.setVeterinary(appointmentForm.getVeterinary());
		appointment.setMoment((new Date(System.currentTimeMillis() - 1000)));
		appointment.setPet(appointmentForm.getPet());
		appointment.setReason(appointmentForm.getReason());
		return appointment;
	}

	//Devuelve las citas del principal que la fecha sea superior a las de hoy
	public Collection<Appointment> findByPrincipalNoExpired() {
		Collection<Appointment> result;
		Customer principal = customerService.findByPrincipal();
		result = appointmentRepository.findByPrincipalNoExpired(principal);
		return result;
	}	

	
	//Borra una cita
	public void cancelAppointment(int appointmentId) {
		Assert.isTrue(actorService.isCustomer() || actorService.isAdministrator() || actorService.isVeterinary());
		Appointment appointment = findOne(appointmentId);
		delete(appointment);
	}

	//Borrar una cita siendo veterinario
	public void cancelAppointmentVeterinary(int appointmentId) {
		sendVeterinaryMessageCancel(appointmentId);
		cancelAppointment(appointmentId);
	}
	public void sendVeterinaryMessageCancel(int appointmentId){		
		Appointment appointment = findOne(appointmentId);
		Veterinary veterinary = veterinaryService.findByPrincipal();
		Customer customer = appointment.getPet().getCustomer();
		Message message = messageService.create(veterinary,customer,"APPOINTMENT CANCEL", "Your appointment "+appointment.getDay().toString()+" has been cancelled. Sorry about that.");
		messageService.sendMessage(message);
	}

}
