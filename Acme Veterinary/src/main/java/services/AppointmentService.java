package services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.mchange.lang.IntegerUtils;

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
		result.setMoment((new Date(System.currentTimeMillis() - 1000)));
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
		Assert.isTrue(checkAppointment(appointment));
		return appointmentRepository.save(appointment);
	}


	public void delete(Appointment appointment) {
		appointmentRepository.delete(appointment);
	}

	// Other business methods -------------------------------------------------
	// Devuelve todas las citas de veterinario principal
	public Collection<Appointment> findAllOwn() {
		Assert.isTrue(actorService.isVeterinary());
		Collection<Appointment> result;
		Veterinary veterinary;
		veterinary = veterinaryService.findByPrincipal();
		result = appointmentRepository.findAllOwn(veterinary);
		return result;
	}

	// Devuelve todas las citas de veterinario principal NO EXPIRADAS y
	// ordenadas por el dia y la hora
	public Collection<Appointment> findAllOwnNoExpired() {
		Assert.isTrue(actorService.isVeterinary());
		Collection<Appointment> result;
		Veterinary veterinary;
		veterinary = veterinaryService.findByPrincipal();
		result = appointmentRepository.findAllOwnNoExpired(veterinary);
		return result;
	}

	// Devuelve todas las citas de veterinario principal NO EXPIRADAS y
	// ordenadas por el dia y la hora
	public Collection<Appointment> findAllOwnNoExpired(Veterinary veterinary) {
		Assert.isTrue(actorService.isAdministrator());
		Collection<Appointment> result;
		result = appointmentRepository.findAllOwnNoExpired(veterinary);
		return result;
	}

	// Devuelve todas las citas de un veterinario NO EXPIRADAS y ordenadas por
	// el dia y la hora
	public Collection<Appointment> findAllByVeterinaryNoExpired(
			Veterinary veterinary) {
		Collection<Appointment> result;
		result = appointmentRepository.findAllOwnNoExpired(veterinary);
		return result;
	}

	// Este metodo se podrá utilizar sin estar logueado, por eso no lleva
	// asserts
	public boolean getVeterinaryisBooked(String day, String startTime,
			String endTime, Veterinary veterinary) throws ParseException {
		boolean res = false;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dayDate = dateFormat.parse(day);
		Collection<Appointment> appointments = appointmentRepository
				.getVeterinaryIsBooked(dayDate, startTime, endTime, veterinary);
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

		for (int i = 0; i < 15; i++) {
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

	public Appointment reconstruct(AppointmentForm appointmentForm)
			throws ParseException {
		Assert.isTrue(actorService.isCustomer());
		Appointment appointment = create();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date day = dateFormat.parse(appointmentForm.getStartMoment());
		appointment.setDay(day);
		appointment.setStartTime(appointmentForm.getStartTime());
		appointment.setEndTime(appointmentForm.getEndTime());
		appointment.setVeterinary(appointmentForm.getVeterinary());
		appointment.setPet(appointmentForm.getPet());
		appointment.setReason(appointmentForm.getReason());
		return appointment;
	}

	// Devuelve las citas del principal que la fecha sea superior a las de hoy
	public Collection<Appointment> findByPrincipalNoExpired() {
		Collection<Appointment> result;
		Customer principal = customerService.findByPrincipal();
		result = appointmentRepository.findByPrincipalNoExpired(principal);
		return result;
	}

	// Devuelve las citas del principal que la fecha sea inferior a las de hoy,
	// usado para comprobar si un customer ha tenido cita con un veterinario
	// antes de comentar
	public Collection<Appointment> findByPrincipaExpired() {
		Collection<Appointment> result;
		Customer principal = customerService.findByPrincipal();
		result = appointmentRepository.findByPrincipalExpired(principal);
		return result;
	}

	// Borra una cita
	public void cancelAppointment(int appointmentId) {
		Assert.isTrue(actorService.isCustomer()
				|| actorService.isAdministrator()
				|| actorService.isVeterinary());
		Appointment appointment = findOne(appointmentId);
		if (actorService.isCustomer())
			Assert.isTrue(appointment.getPet().getCustomer()
					.equals(actorService.findByPrincipal()));

		delete(appointment);
	}

	// Borrar una cita siendo veterinario
	public void cancelAppointmentVeterinary(int appointmentId) {
		Veterinary veterinary = veterinaryService.findByPrincipal();
		sendVeterinaryMessageCancel(appointmentId, veterinary);
		cancelAppointment(appointmentId);
	}

	// Borrar una cita siendo administrador
	public void cancelAppointmentAdministrator(int appointmentId) {
		Appointment appointment = findOne(appointmentId);
		sendVeterinaryMessageCancel(appointmentId, appointment.getVeterinary());
		cancelAppointment(appointmentId);
	}

	public void sendVeterinaryMessageCancel(int appointmentId,
			Veterinary veterinary) {
		Appointment appointment = findOne(appointmentId);
		Customer customer = appointment.getPet().getCustomer();
		DateFormat ndf = new SimpleDateFormat("dd/MM/yyyy");
		Message message = messageService.create(veterinary, customer,
				"APPOINTMENT CANCEL",
				"Your appointment " + ndf.format(appointment.getDay())
						+ " has been cancelled. Sorry about that.");
		messageService.sendMessage(message);
	}
	//Comprobar que las citas sean validas para crearla
	private boolean checkAppointment(Appointment appointment) {
		boolean result = true;
		// que no sean pasadas
		Calendar c = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		today.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);
		today.set(Calendar.MILLISECOND, 0);
		
		if (appointment.getDay().before(today.getTime())){
			result = false;
		}else if (appointment.getDay().equals(today.getTime())){
			if (IntegerUtils.parseInt(appointment.getStartTime(), 0) <= c.get(Calendar.HOUR)){
				result = false;
			}
		}
		
		return result;
	}
	
	
	public boolean checkDateInView(String day, String startTime) throws ParseException{
		boolean result = true;
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar dayCalendar = Calendar.getInstance();
		dayCalendar.setTime(dateFormat.parse(day));
		dayCalendar.set(Calendar.HOUR_OF_DAY, IntegerUtils.parseInt(startTime, 0));
		Calendar today = Calendar.getInstance();
		if (dayCalendar.before(today.getTime())){
			result = false;
		}
		
		return result;
	}
}
