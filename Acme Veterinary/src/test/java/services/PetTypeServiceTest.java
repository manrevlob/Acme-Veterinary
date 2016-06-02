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
import domain.PetType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class PetTypeServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private PetTypeService petTypeService;

	// Tests -------------------------------------------------

	// Comprobamos que podemos crear un tipo de mascota como administrador
	@Test
	public void testCreateAndSave() {
		Collection<PetType> before;
		Collection<PetType> after;

		authenticate("admin");

		before = petTypeService.findAll();

		PetType petType;
		petType = petTypeService.create();
		petType.setName("TEST");
		petType.setDescription("TEST");
		petTypeService.save(petType);

		after = petTypeService.findAll();

		Assert.isTrue(before.size() < after.size());

		unauthenticate();
	}

	// Test Negativos ----------------------------------------

	// Comprobamos que no podemos crear un tipo de mascota con un rol distinto a
	// administrador
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNeg() {
		authenticate("customer1");

		PetType petType;
		petType = petTypeService.create();
		petType.setName("TEST");
		petType.setDescription("TEST");
		petTypeService.save(petType);

		unauthenticate();
	}

	// Comprobamos que no podemos crear un tipo de mascota con un nombre ya
	// existente
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNeg2() {
		authenticate("admin");

		// Creamos un tipo de mascota
		PetType petType1;
		petType1 = petTypeService.create();
		petType1.setName("TEST");
		petType1.setDescription("TEST");
		petTypeService.save(petType1);

		// Intentamos crear el tipo de mascota de nuevo
		PetType petType2;
		petType2 = petTypeService.create();
		petType2.setName("TEST");
		petType2.setDescription("TEST");
		petTypeService.save(petType2);

		unauthenticate();
	}

}