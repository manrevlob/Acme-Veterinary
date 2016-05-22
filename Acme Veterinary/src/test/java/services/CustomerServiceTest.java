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
import domain.Customer;
import domain.History;
import domain.Pet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CustomerServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PetService petService;
	

	// Tests -------------------------------------------------

	// Nos registramos en el sistema como nuevo customer
	@Test
	public void testCreateCustomer(){
		
		Customer customer;
		customer = customerService.create();
		customer.setName("TEST");
		customer.setSurname("TEST");
		customer.setPhone("123456789");
		customer.setEmail("email@email.com");
		customerService.save(customer);
		
		Assert.notNull(customerService.findOne(customer.getId()));
		
	}
	
	
	// Comprobamos que un usuario puede cambiar su informacíón personal
	
	@Test
	public void testChangePersonalInformation(){
		authenticate("customer1");
		
		Customer customer;
		//Buscamos el customer principal
		customer = customerService.findByPrincipal();
		//Modificamos el nombre del usuario logueado
		customer.setName("TEST");
		customerService.save(customer);
		//Obtenemos el nombre nuevo 
		String nameAfter = customer.getName();
		
		//Comprobamos que no son el mismo
		Assert.isTrue(nameAfter == "TEST");
		
		unauthenticate();
		
	}
	
	// Comprobamos que un usuario puede cambiar los datos de su mascota
	
	@Test
	public void testChangePetInformation(){
		authenticate("customer1");
		
		Pet pet;
		//Seleccionamos la mascota
		pet = petService.findOne(57);
		//Le modificamos el nombre
		pet.setName("TEST");
		petService.save(pet);
		//Obtenemos el nuevo nombre
		String nameAfter = pet.getName();
		//Comprobamos que los nombres no son iguales
		Assert.isTrue(nameAfter == "TEST");
		
		unauthenticate();
		
	}
	
	//Comprobamos que un usuario puede ver el historial médico de sus mascotas.
	
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

		// Comprobamos que un usuario no puede cambiar la información personal de otro usuario
		@Test(expected = NullPointerException.class)
		@Rollback(true)
		public void testChangePersonalInformationNegative() {
	
			authenticate("customer1");
			
			Customer customerDiferente = customerService.findOne(50);
			
			//Modificamos el nombre del otro usuario
			customerDiferente.setName("TEST");
			customerService.save(customerDiferente);
			
			unauthenticate();


		}
		
		// Comprobamos que un usuario no puede añadir una mascota sin rellenar los campos requeridos
		
			//TODO pasa el test y no debería, ERROR : le dejo el campo nombre y cumpleaños sin poner y me pasa el test
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
			
				//TODO pasa el test y no debería, ERROR : puedo eliminar mascotas que no son mías.
				@Test()
				@Rollback(true)
				public void testDeletePetNegative() {

					authenticate("customer1");

					Pet pet;
					pet = petService.findOne(59);
					petService.delete(pet.getId());
					
					unauthenticate();

				}
				
			//Comprobamos que un usuario no puede ver el historial médico de un animal que no es suyo.
				
				@Test()
				@Rollback(true)
				public void testSeeHistoryPetNegative() {

					authenticate("customer1");

					Pet pet;
					pet = petService.findOne(59);
					pet.getHistories();
					
					unauthenticate();

				}
				
}