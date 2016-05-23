package services;

import java.sql.Date;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Appointment;
import domain.Pet;
import domain.Veterinary;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class AppointmentServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private VeterinaryService veterinaryService;

	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private PetService petService;

	// Tests -------------------------------------------------

	// Comprobamos que un usuario puede pedir cita para un veterinario

	@Test
	public void testCreateAppointment() {

		// Nos logueamos como customer1
		authenticate("customer1");

		Collection<Appointment> appointments;
		appointments = appointmentService.findByPrincipalNoExpired();

		Veterinary veterinary;
		Pet pet = petService.findOne(57);
		veterinary = veterinaryService.findOne(49);
		Appointment appointment = appointmentService.create();
		appointment.setVeterinary(veterinary);
		appointment.setDay(new Date(12 / 13 / 2016));
		appointment.setEndTime("21:00:00");
		appointment.setPet(pet);
		appointment.setReason("TEST");
		appointment.setStartTime("20:00:00");
		appointmentService.save(appointment);

		Assert.isTrue(appointments.contains(appointment));

		unauthenticate();

	}

	// Comprobamos que un usuario puede cancelar una cita

	@Test
	public void testCancelAppointment() {
		authenticate("customer1");

		appointmentService.cancelAppointment(67);

		unauthenticate();
	}
	

	// Test Negativos ----------------------------------------

	// Comprobamos que un usuario no puede cancelar una sita que no es suya
	
	//TODO pasa el test y no debería, ERROR
	@Test()
	@Rollback(true)
	public void testCancelAppointmentNegative() {

		authenticate("customer1");

		Appointment appointment;
		
		appointment = appointmentService.findOne(65);

		appointmentService.cancelAppointment(appointment.getId());

		unauthenticate();

	}
	
	//TODO Comprobar que no se puede crear una cita con un veterinario que no existe.
	
	

}