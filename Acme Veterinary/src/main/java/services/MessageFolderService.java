package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageFolderRepository;
import domain.Message;
import domain.MessageFolder;

@Service
@Transactional
public class MessageFolderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageFolderRepository messageFolderRepository;

	// Supporting services ----------------------------------------------------

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
}