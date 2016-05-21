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
import domain.MessageFolder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class MessageFolderServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private MessageFolderService messageFolderService;
	@Autowired
	private ActorService actorService;

	// Tests -------------------------------------------------

	// Creamos un nueva carpeta para un usuario logueado
	@Test
	public void testCreateAndSave() {
		Collection<MessageFolder> before;
		Collection<MessageFolder> after;
		MessageFolder messageFolder;

		// Nos logueamos como Administrador
		authenticate("admin");

		// Recogemos el total de carpetas que hay en el sistema antes de la
		// creacion
		before = messageFolderService.findByActor();

		// Creamos la carpeta
		messageFolder = messageFolderService.create();
		messageFolder.setName("TEST");
		messageFolder = messageFolderService.save(messageFolder);
		messageFolderService.assignFolderToActor(messageFolder);

		// Recogemos el total de carpetas que hay en el sistema despues de la
		// creacion
		after = messageFolderService.findByActor();

		Assert.isTrue(before.size() < after.size());

		unauthenticate();
	}

	// Eliminar una carpeta de un usuario logueado
	@Test
	public void testDeleteOfActor() {
		Collection<MessageFolder> before;
		Collection<MessageFolder> after;
		Collection<MessageFolder> afterDelete;
		MessageFolder messageFolder;

		// Nos logueamos como Administrador
		authenticate("admin");

		// Primero vamos a crear la carpeta que vamos a eliminar
		// Recogemos el total de carpetas que hay en el sistema antes de la
		// creacion y su posterior eliminacion
		before = messageFolderService.findByActor();

		// Creamos la carpeta
		messageFolder = messageFolderService.create();
		messageFolder.setName("TEST");
		messageFolder = messageFolderService.save(messageFolder);
		messageFolderService.assignFolderToActor(messageFolder);

		// Recogemos el total de carpetas que hay en el sistema despues de la
		// creacion
		after = messageFolderService.findByActor();

		Assert.isTrue(before.size() < after.size());

		// Eliminamos la carpeta que hemos creado
		messageFolderService.deleteOfActor(messageFolder);

		afterDelete = messageFolderService.findByActor();

		// Al haber creado y eliminado la misma carpeta, el tamaño de las
		// carpetas del usuario administrador debe ser el mismo
		Assert.isTrue(before.size() == afterDelete.size());

		unauthenticate();
	}

	// Test Negativos ----------------------------------------

	// Comprobamos que no podemos obtener las carpetas sin estar logueado
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void testFindByActor(){
		unauthenticate();
		messageFolderService.findByActor();
	}
	
	// Comprobamos que no podemos obtener la carpeta inbox sin estar logueado
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void testFindFolder(){
		Actor actor;
		unauthenticate();
		actor = actorService.findByPrincipal();
		messageFolderService.findFolder(actor, "In box");
	}
	
	// Comprobamos que no podemos asignar una carpeta a un usuario no logueado
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void testAssignFolderToActorNegative(){
		unauthenticate();
		// MessageFolder1 ID = 9
		messageFolderService.assignFolderToActor(messageFolderService.findOne(9));
	}
	
	// Comprobamos que no podemos eliminar una carpeta de un usuario sin estar logueado
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void testDeleteOfActorNegative(){
		unauthenticate();
		// MessageFolder1 ID = 9
		messageFolderService.deleteOfActor(messageFolderService.findOne(9));
	}
	
	
}