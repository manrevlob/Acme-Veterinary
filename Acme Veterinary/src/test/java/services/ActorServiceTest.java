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
import domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ActorServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private ActorService actorService;

	// Tests -------------------------------------------------

	// Probamos que el usuario logeado tenga privilegios de administrador
	@Test
	public void testIsAdministrator() {
		boolean res;
		authenticate("admin");
		res = actorService.isAdministrator();
		Assert.isTrue(res);
		authenticate(null);
	}

	// Probamos que el usuario logeado tenga privilegios de user
	@Test
	public void testIsCustomer() {
		boolean res;
		authenticate("customer1");
		res = actorService.isCustomer();
		Assert.isTrue(res);
		authenticate(null);
	}

	// Probamos que el usuario logeado tenga privilegios de veterinario
	@Test
	public void testIsVeterinary() {
		boolean res;
		authenticate("veterinary1");
		res = actorService.isVeterinary();
		Assert.isTrue(res);
		authenticate(null);
	}

	// Probamos que se devuelva correctamente el usuario logueado
	@Test
	public void testFindByPrincipal() {
		Actor actor;
		authenticate("customer1");
		actor = actorService.findByPrincipal();
		Assert.notNull(actor);
		authenticate(null);
	}

	// Probamos a encontrar todos los usuarios del sistema salvo nosotros mismos
	// este método se usa para a la hora de enviar un mensaje traernos los
	// posibles destinatarios
	@Test
	public void testFindAllExceptMe() {
		Collection<Actor> actors;
		authenticate("admin");
		actors = actorService.findAllExceptMe();
		Assert.isTrue(actors.size() == 5);
		authenticate(null);
	}

	// Test Negativos ----------------------------------------

	// Comprobamos que el método falla al no estar logueado
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void testFindByPrincipalNegative() {
		unauthenticate();
		actorService.findByPrincipal();
	}

	// Comprobamos que no funciona sin estar logueado
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void testFindAllExceptMeNegtive() {
		unauthenticate();
		actorService.findAllExceptMe();
	}

}