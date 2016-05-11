package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AppointmentRepository;
import domain.Appointment;

@Service
@Transactional
public class AppointmentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AppointmentRepository appointmentRepository;

	// Supporting services ----------------------------------------------------

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
}
