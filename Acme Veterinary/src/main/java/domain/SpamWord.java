package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class SpamWord extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public SpamWord() {
		super();
	}

	// Attributes ---------------------------------------------------------

	private String keyWord;

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

}
