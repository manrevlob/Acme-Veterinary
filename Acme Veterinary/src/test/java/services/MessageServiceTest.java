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
import domain.Message;
import domain.MessageFolder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class MessageServiceTest extends AbstractTest {

	// Service Supported ------------------------------------

	@Autowired
	private MessageService messageService;
	@Autowired
	private MessageFolderService messageFolderService;
	@Autowired
	private ActorService actorService;

	// Tests -------------------------------------------------

	// Obtenemos todos los mensajes de una carpeta
	@Test
	public void testFindByMessageFolderId() {
		Collection<Message> messagesInFolder;
		MessageFolder messageFolder;
		authenticate("admin");
		// ID del outbox del admin
		messageFolder = messageFolderService.findOne(27);
		messagesInFolder = messageService.findAllByFolder(messageFolder);
		Assert.isTrue(messagesInFolder.size() == 1);
		unauthenticate();
	}

	// Enviamos un mensaje desde el admin al customer1
	@Test
	public void testSendMessage() {
		Message message;
		Collection<Message> before;
		Collection<Message> after;

		authenticate("admin");

		before = messageService.findAll();

		// Creamos un mensajes
		message = messageService.create();
		message.setSubject("Test");
		message.setBody("Test");
		message.setSender(actorService.findByPrincipal());
		// ID del customer1, al que enviaremos el mensaje
		message.setRecipient(actorService.findOne(53));
		messageService.sendMessage(message);

		after = messageService.findAll();

		Assert.isTrue(before.size() < after.size());

		unauthenticate();

	}


	// Obtenemos si el destinatario de un mensaje es el correcto
	@Test
	public void testIsRecipient() {
		Message message;
		boolean result;

		// Customer1 es el destinatario
		authenticate("customer1");

		// ID del mensaje 1
		message = messageService.findOne(61);

		result = messageService.isRecipient(message,
				actorService.findByPrincipal());

		// ID del mensaje 1 y del trashbox del administrador respectivamente
		Assert.isTrue(result);

		unauthenticate();
	}

	// Enviamos un mensaje a la papelera
	@Test
	public void testSendToTrashbox() {
		Message message;

		// Admin es el propietario del mensaje
		authenticate("admin");

		// ID del mensaje 1
		message = messageService.findOne(61);
		messageService.moveToTrashbox(message);
		// ID del mensaje 1 y del trashbox del administrador respectivamente
		Assert.isTrue(messageService.findOne(61).getMessageFolder() == messageFolderService
				.findOne(28));
		unauthenticate();
	}

	// Test Negativos ----------------------------------------

	// Comprobamos que no podemos buscar todos los mensajes de una carpeta que
	// no existe
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void testFindByMessageFolderIdNegative() {
		MessageFolder messageFolder;

		authenticate("admin");

		// ID de un messageFolder que no existe
		messageFolder = messageFolderService.findOne(100000000);
		messageService.findAllByFolder(messageFolder);

		unauthenticate();

	}

	// Tratamos de enviar un mensaje con el recipient y el sender iguales
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void testSendMessageNegative() {
		Message message;
		authenticate("customer1");

		message = messageService.create();
		message.setSubject("Test");
		message.setBody("Test");
		message.setSender(actorService.findByPrincipal());

		// ID del customer1, nosotros mismos, al que trataremos de enviar el
		// mensaje

		message.setRecipient(actorService.findOne(47));

		messageService.sendMessage(message);

		unauthenticate();
	}
		
		// Tratamos de enviar un mensaje con un sender diferente al usuario logueado
		@Test(expected = IllegalArgumentException.class)
		@Rollback(true)
		public void testSendMessageSenderNegative() {
			Message message;
			authenticate("admin");

			message = messageService.create();
			message.setSubject("Test");
			message.setBody("Test");
			// Id del customer2
			message.setSender(actorService.findOne(54));

			// ID del customer1, nosotros mismos, al que trataremos de enviar el
			// mensaje

			message.setRecipient(actorService.findOne(53));

			messageService.sendMessage(message);

			unauthenticate();


	}

	// Tratamos de enviar un mensaje sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void testSendMessage2() {
		Message message;

		unauthenticate();

		message = messageService.create();
		message.setSubject("Test");
		message.setBody("Test");

		// ID del admin, al que enviaremos el mensaje
		message.setRecipient(actorService.findOne(46));

		messageService.sendMessage(message);

	}

	

	// Comprobamos que un usuario no puede mandar a la papelera un mensaje del
	// cual no es propietario
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void testSendToTrashboxNegative() {
		Message message;

		// Admin es el propietario del mensaje
		authenticate("customer2");

		// ID del mensaje 1
		message = messageService.findOne(55);

		messageService.moveToTrashbox(message);

		unauthenticate();

	}

}