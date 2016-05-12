package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageFolderRepository;
import domain.Actor;
import domain.Message;
import domain.MessageFolder;

@Service
@Transactional
public class MessageFolderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageFolderRepository messageFolderRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private MessageService messageService;

	// Constructors -----------------------------------------------------------

	public MessageFolderService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public MessageFolder create() {
		MessageFolder result;
		Collection<Message> messages = new ArrayList<Message>();
		result = new MessageFolder();

		result.setMessages(messages);
		result.setSystem(false);

		return result;
	}

	public MessageFolder findOne(int messageFolderId) {
		MessageFolder result;
		result = messageFolderRepository.findOne(messageFolderId);
		return result;
	}

	public Collection<MessageFolder> findAll() {
		Collection<MessageFolder> result;
		result = messageFolderRepository.findAll();
		return result;
	}

	public Collection<MessageFolder> saveAll(
			Collection<MessageFolder> messageFolders) {
		Assert.notEmpty(messageFolders);
		return messageFolderRepository.save(messageFolders);
	}
	
	public MessageFolder save(MessageFolder messageFolder) {
		Assert.notNull(messageFolder);
		return messageFolderRepository.save(messageFolder);
	}
	
	public void delete(MessageFolder messageFolder) {
		messageFolderRepository.delete(messageFolder);
	}

	
	// Other business methods -------------------------------------------------
		public Collection<MessageFolder> findByActor() {
			Assert.isTrue(actorService.isAuthenticated());
			Actor actor;
			Collection<MessageFolder> result;

			actor = actorService.findByPrincipal();

			result = messageFolderRepository.findByActor(actor.getId());
			Assert.notEmpty(result);

			return result;
		}

		public MessageFolder findFolder(Actor actor, String folder) {
			Assert.isTrue(actorService.isAuthenticated());
			Assert.notNull(folder);
			Assert.notNull(actor);
			MessageFolder result;

			result = messageFolderRepository
					.findByActorAndNameFolder(actor, folder);
			Assert.notNull(result);

			return result;
		}

		public Actor assignFolderToActor(MessageFolder messageFolder) {
			Assert.isTrue(actorService.isAuthenticated());
			Assert.notNull(messageFolder);
			Actor result;
			Collection<MessageFolder> messageFolders;

			result = actorService.findByPrincipal();
			messageFolders = result.getMessageFolders();
			messageFolders.add(messageFolder);
			result.setMessageFolders(messageFolders);
			result = actorService.save(result);

			return result;
		}

		// this method will delete a messageFolder sending the messages his to the
		// trashbox
		public void deleteOfActor(MessageFolder messageFolder) {
			Assert.isTrue(actorService.isAuthenticated());
			Assert.notNull(messageFolder);
			Actor actor;
			Collection<MessageFolder> messageFolders;

			actor = actorService.findByPrincipal();

			messageFolders = actor.getMessageFolders();

			if (messageFolders.contains(messageFolder)) {

				messageFolder = findOne(messageFolder.getId());
				deleteAllMessage(messageFolder.getMessages());

				Collection<MessageFolder> aux = messageFolders;
				aux.remove(messageFolder);
				actor.setMessageFolders(aux);

				actorService.save(actor);

			}
		}

		public void deleteAllMessage(Collection<Message> messages) {
			List<Message> list = new ArrayList<Message>(messages);
			for (Message m : list) {
				messageService.delete(m);
			}
		}

		public boolean isOwner(int messageFolderId) {
			boolean result = false;
			Actor actor = actorService.findByPrincipal();
			MessageFolder messageFolder;
			messageFolder = messageFolderRepository.findOne(messageFolderId);
			if (actor.getMessageFolders().contains(messageFolder)) {
				result = true;
			}
			return result;

		}
		
		public Collection<MessageFolder> createDefaultFolders(){
			Collection<MessageFolder> result;
			MessageFolder inbox;
			MessageFolder outbox;
			MessageFolder trashbox;
			MessageFolder spambox;
			
			result = new ArrayList<MessageFolder>();

			inbox = create();
			outbox = create();
			trashbox = create();
			spambox = create();

			inbox.setSystem(true);
			outbox.setSystem(true);
			trashbox.setSystem(true);
			spambox.setSystem(true);


			inbox.setName("In box");
			outbox.setName("Out box");
			trashbox.setName("Trash box");
			spambox.setName("Spam box");

			result.add(inbox);
			result.add(outbox);
			result.add(trashbox);
			result.add(spambox);

			result = saveAll(result);
			
			return result;
			
		}
}
