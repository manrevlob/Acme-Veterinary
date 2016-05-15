package forms;

import javax.persistence.Access;

import javax.persistence.AccessType;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Access(AccessType.PROPERTY)
public class SearchForm {

	// Constructors ---------------------------------------------------

	public SearchForm() {
		super();
	}

	// Attributes -----------------------------------------------------
	private String keyword;

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}