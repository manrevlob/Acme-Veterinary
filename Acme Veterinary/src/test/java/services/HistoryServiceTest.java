package services;

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
import domain.History;
import domain.Pet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class HistoryServiceTest extends AbstractTest {

	// Service Supported ------------------------------------


	@Autowired
	private PetService petService;

	// Tests -------------------------------------------------

	// Comprobamos que un usuario puede ver el historial m�dico de sus mascotas.

	@Test()
	@Rollback(true)
	public void testSeeHistoryPet() {

		authenticate("customer1");

		Collection<History> histories;

		Pet pet;
		pet = petService.findOne(60);
		histories = pet.getHistories();

		Assert.notNull(histories);

		unauthenticate();

	}

	// Test Negativos ----------------------------------------

	// Comprobamos que un usuario no puede ver el historial m�dico de un animal
	// que no es suyo.

	@Test()
	@Rollback(true)
	public void testSeeHistoryPetNegative() {

		authenticate("customer1");

		Pet pet;
		pet = petService.findOne(59);
		pet.getHistories();

		unauthenticate();

	}

	//TODO a�adir uno m�s
}