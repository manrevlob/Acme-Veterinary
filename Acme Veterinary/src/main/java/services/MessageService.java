package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Message;
import domain.MessageFolder;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageRepository messageRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private MessageFolderService messageFolderService;

	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Message create() {
		Message result;
		result = new Message();
		result.setMoment(new Date(System.currentTimeMillis() - 1000));
		return result;

	}

	public Message create(Actor actor, MessageFolder messageFolder) {
		Message result;
		result = new Message();
		result.setMoment(new Date(System.currentTimeMillis() - 1000));
		result.setMessageFolder(messageFolder);
		result.setSender(actor);
		return result;
	}

	public Message save(Message message) {
		Assert.isTrue(actorService.isAuthenticated());
		Assert.isTrue(message.getSender() != message.getRecipient());
		Assert.notNull(message);
		message.setMoment(new Date(System.currentTimeMillis() - 1000));
		return messageRepository.save(message);
	}

	public void delete(Message message) {
		Assert.isTrue(actorService.isAuthenticated());
		Assert.notNull(message);

		if (message.getMessageFolder().getName().equals("Trash box")) {
			messageRepository.delete(message);
		} else {
			moveToTrashbox(message);
		}
	}

	public Message findOne(int messageId) {
		Assert.isTrue(actorService.isAuthenticated());
		Assert.isTrue(messageId > 0);
		Message result;
		result = messageRepository.findOne(messageId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Message> findAll() {
		Collection<Message> result;
		result = messageRepository.findAll();
		return result;
	}

	// Other business methods
	public Collection<Message> findAllMine() {
		Assert.isTrue(actorService.isAuthenticated());
		Collection<Message> result;

		Actor actor;
		actor = actorService.findByPrincipal();

		result = messageRepository.findByActor(actor);
		return result;
	}

	public Collection<Message> findAllByFolder(MessageFolder messageFolder) {
		Assert.isTrue(actorService.isAuthenticated());
		Assert.notNull(messageFolder);

		Collection<Message> result;
		Actor actor;
		actor = actorService.findByPrincipal();

		result = messageRepository.findByActorAndFolder(actor, messageFolder);
		return result;
	}

	public void sendMessage(Message message) {
		Actor sender;
		Actor recipient;
		Collection<Message> messages;
		MessageFolder senderOutbox;

		Assert.notNull(message);

		sender = message.getSender();
		recipient = message.getRecipient();
		senderOutbox = messageFolderService.findFolder(sender, "Out box");

		receiveMessage(message, recipient);

		message.setMessageFolder(senderOutbox);
		message.setMoment(new Date(System.currentTimeMillis() - 1000));

		messages = findAllByFolder(senderOutbox);
		messages.add(message);
		senderOutbox.setMessages(messages);

		save(message);
		messageFolderService.save(senderOutbox);

	}
	
//	public void sendAutoReplyMessage(String messageText, Actor sender, Actor recipient) {
//		Assert.notNull(sender);
//		Assert.notNull(recipient);
//		
//		Collection<Message> messages;
//		MessageFolder senderOutbox;
//		Message message;
//		message = create();
//		
//		message.setSender(sender);
//		message.setRecipient(recipient);
//		message.setSubject("AUTO-REPLY --");
//		message.setBody(messageText);
//		
//		senderOutbox = messageFolderService.findFolder(sender, "Auto-Reply box");
//
//		receiveMessage(message, recipient);
//
//		message.setMessageFolder(senderOutbox);
//
//		messages = findAllByFolder(senderOutbox);
//		messages.add(message);
//		senderOutbox.setMessages(messages);
//
//		save(message);
//		messageFolderService.save(senderOutbox);
//
//	}

	public void receiveMessage(Message message, Actor recipient) {
		Message messageReceived;
		MessageFolder recipientInbox;
		Collection<Message> messages;

		messageReceived = new Message();
		messageReceived.setBody(message.getBody());
		messageReceived.setMoment(message.getMoment());
		messageReceived.setSubject(message.getSubject());
		messageReceived.setRecipient(recipient);
		messageReceived.setSender(message.getSender());

		recipientInbox = messageFolderService.findFolder(recipient, "In box");

		messageReceived.setMessageFolder(recipientInbox);

		messages = messageRepository.findByActorAndFolder(recipient,
				recipientInbox);
		messages.add(messageReceived);
		recipientInbox.setMessages(messages);

		save(messageReceived);
		messageFolderService.save(recipientInbox);
		

	}

	public Message createReply(Message message) {
		Message result;

		Assert.notNull(message);
		Assert.isTrue(actorService.findByPrincipal().equals(
				message.getRecipient()));
	
		result = create();

		result.setSubject("RE: " + message.getSubject());
		result.setRecipient(message.getSender());
		result.setSender(actorService.findByPrincipal());

		return result;
	}

	public boolean isRecipient(Message message, Actor actor) {
		return message.getRecipient().equals(actor);
	}

	public void moveToTrashbox(Message message) {
		Assert.isTrue(isOwner(message));
		MessageFolder trashbox;
		MessageFolder actualFolder;
		Collection<Message> messagesTrash;
		Collection<Message> messagesActual;
		Actor actor;
		Message mt;

		actualFolder = message.getMessageFolder();
		messagesActual = actualFolder.getMessages();
		messagesActual.remove(message);
		actualFolder.setMessages(messagesActual);
		messageFolderService.save(actualFolder);

		actor = actorService.findByPrincipal();
		trashbox = messageFolderService.findFolder(actor, "Trash box");
		messagesTrash = trashbox.getMessages();
		mt = message;
		mt.setMessageFolder(trashbox);
		messagesTrash.add(mt);
		trashbox.setMessages(messagesTrash);

		save(mt);
		messageFolderService.save(trashbox);
	}

	public void moveToFolder(Message message, MessageFolder messageFolder) {
		Assert.notNull(message);
		Assert.notNull(messageFolder);
		Assert.isTrue(message.getSender() == actorService.findByPrincipal()
				|| message.getRecipient() == actorService.findByPrincipal());
		MessageFolder actualFolder;
		Collection<Message> messagesDestino;
		Collection<Message> messagesActual;
		Message mt;

		actualFolder = message.getMessageFolder();
		messagesActual = actualFolder.getMessages();
		messagesActual.remove(message);
		actualFolder.setMessages(messagesActual);
		messageFolderService.save(actualFolder);

		messagesDestino = messageFolder.getMessages();
		mt = message;
		mt.setMessageFolder(messageFolder);
		messagesDestino.add(mt);
		messageFolder.setMessages(messagesDestino);

		save(mt);
		messageFolderService.save(messageFolder);

	}

	public boolean isOwner(Message message) {
		boolean result = false;
		Actor actor = actorService.findByPrincipal();
		if (actor.getMessageFolders().contains(message.getMessageFolder())) {
			result = true;
		}
		return result;
	}
}
