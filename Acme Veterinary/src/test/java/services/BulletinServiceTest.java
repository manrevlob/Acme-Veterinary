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
import domain.Bulletin;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class BulletinServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private BulletinService bulletinService;

	// Tests -------------------------------------------------

	// Comprobamos que podemos crear un bulletin logueado como administrador
	@Test
	public void testCreateAndSave() {
		Collection<Bulletin> before;
		Collection<Bulletin> after;

		authenticate("admin");

		// Obtenemos la Id de la clinica1
		int clinicId = 50;

		before = bulletinService.findAllTime();

		Bulletin bulletin;
		bulletin = bulletinService.create(clinicId);
		bulletin.setDescription("TEST");
		bulletinService.save(bulletin);

		after = bulletinService.findAllTime();

		Assert.isTrue(before.size() < after.size());

		unauthenticate();
	}

	// Comprobamos que podemos eliminar un bulletin estando logueados como
	// administrador
	@Test
	public void testDelete() {
		authenticate("admin");

		// Obtenemos la Id del bulletin1
		int bulletinId = 74;

		Bulletin bulletin;
		bulletin = bulletinService.findOne(bulletinId);
		bulletinService.delete(bulletin);

		Assert.isTrue(bulletin.getIsDeleted());

		unauthenticate();
	}

	// Test Negativos ----------------------------------------

	// Comprobamos que no podemos crear un bulletin sin estar logueados con el
	// rol de administrador
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNeg2() {
		authenticate("customer1");

		// Obtenemos la Id de la clinica1
		int clinicId = 50;

		Bulletin bulletin;
		bulletin = bulletinService.create(clinicId);
		bulletin.setDescription("TEST");
		bulletinService.save(bulletin);

		unauthenticate();
	}

	// Comprobamos que no podemos eliminar un bulletin que ya haya sido
	// eliminado antes
	@Rollback(true)
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNeg() {
		authenticate("admin");

		// Obtenemos la Id del bulletin2 que esta eliminado
		int bulletinId = 75;

		Bulletin bulletin;
		bulletin = bulletinService.findOne(bulletinId);
		bulletinService.delete(bulletin);

		unauthenticate();
	}

}