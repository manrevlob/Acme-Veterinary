package services;

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
import domain.Pet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class PetServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PetService petService;

	// Tests -------------------------------------------------

	// Comprobamos que un usuario puede cambiar los datos de su mascota

	@Test
	public void testChangePetInformation() {
		authenticate("customer1");

		Pet pet;
		// Seleccionamos la mascota
		pet = petService.findOne(57);
		// Le modificamos el nombre
		pet.setName("TEST");
		petService.save(pet);
		// Obtenemos el nuevo nombre
		String nameAfter = pet.getName();
		// Comprobamos que los nombres no son iguales
		Assert.isTrue(nameAfter == "TEST");

		unauthenticate();

	}

	
	// Test Negativos ----------------------------------------

	// Comprobamos que un usuario no puede añadir una mascota sin rellenar los
	// campos requeridos

	// TODO pasa el test y no debería, ERROR : le dejo el campo nombre y
	// cumpleaños sin poner y me pasa el test
	@Test()
	@Rollback(true)
	public void testCreatePetNegative() {

		authenticate("customer1");

		Pet pet;
		pet = petService.create();
		pet.setCustomer(customerService.findByPrincipal());
		petService.save(pet);

		unauthenticate();

	}

	// Comprobamos que un usuario no puede borrar una mascota que no es suya

	// TODO pasa el test y no debería, ERROR : puedo eliminar mascotas que no
	// son mías.
	@Test()
	@Rollback(true)
	public void testDeletePetNegative() {

		authenticate("customer1");

		Pet pet;
		pet = petService.findOne(59);
		petService.delete(pet.getId());

		unauthenticate();

	}

}