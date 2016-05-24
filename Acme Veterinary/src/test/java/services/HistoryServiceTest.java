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
import domain.History;
import domain.Pet;
import domain.Treatment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class HistoryServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private HistoryService historyService;
	@Autowired
	private PetService petService;
	@Autowired
	private AppointmentService appointmentService;

	// Tests -------------------------------------------------

	// Comprobamos que un usuario puede ver el historial médico de sus mascotas.

	@Test
	public void testSeeHistoryPet() {

		authenticate("customer1");

		Collection<History> histories;

		Pet pet;
		//Usamos el id del pet1
		pet = petService.findOne(57);
		histories = pet.getHistories();

		Assert.notNull(histories);

		unauthenticate();

	}

	// Test Negativos ----------------------------------------

	// Comprobamos que un usuario no puede ver el historial médico de un animal
	// que no es suyo.

	
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void testSeeHistoryPetNegative() {

		authenticate("customer1");
		Pet pet;
		//Usamos el id del pet3
		pet = petService.findOne(59);
		historyService.findAllByPet(pet.getId());

		unauthenticate();

	}

	
	// Comprobamos que un usuario que no es veterinario no puede ver crear un historial médico de un animal.

	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void testCreateHistoryPetNegative() {

		authenticate("customer1");
		Appointment appointment;
		History history;
		//Usamos el id del appointment 5
		appointment = appointmentService.findOne(67);
		history = historyService.create();
		history.setAppointment(appointment);
		history.setDiagnosis("TEST");
		Treatment treatment = new Treatment();
		treatment.setDescription("TEST");
		treatment.setEndMoment(new Date(07/13/2016));
		treatment.setStartMoment(new Date(07/30/2016));
		history.setTreatment(treatment);
		
		historyService.save(history);

		unauthenticate();

	}
	
}