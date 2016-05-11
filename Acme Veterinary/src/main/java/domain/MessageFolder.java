package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class MessageFolder extends DomainEntity {

	// Constructors -----------------------------------------------------------
	public MessageFolder() {
		super();
	}

	// Identification ---------------------------------------------------------

	private String name;
	private Boolean system;

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getSystem() {
		return system;
	}

	public void setSystem(Boolean system) {
		this.system = system;
	}

	// RelationsShips ---------------------------------------------------------

	private Collection<Message> messages;

	@Valid
	@OneToMany(mappedBy = "messageFolder")
	public Collection<Message> getMessages() {
		return messages;
	}

	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}

	@Override
	public String toString() {
		return "MessageFolder [name=" + name + "]";
	}

}
