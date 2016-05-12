package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.constraints.NotNull;

import domain.Message;
import domain.MessageFolder;

@Access(AccessType.PROPERTY)
public class MoveMessageForm {

	private Message message;
	private MessageFolder messageFolder;

	@NotNull
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@NotNull
	public MessageFolder getMessageFolder() {
		return messageFolder;
	}

	public void setMessageFolder(MessageFolder messageFolder) {
		this.messageFolder = messageFolder;
	}

}
