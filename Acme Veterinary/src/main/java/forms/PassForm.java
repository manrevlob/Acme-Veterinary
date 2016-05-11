package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Access(AccessType.PROPERTY)
public class PassForm {

	private String passLast;
	private String passNew;
	private String passConf;

	@Size(min = 5, max = 32)
	@NotBlank
	public String getPassLast() {
		return passLast;
	}

	public void setPassLast(String passLast) {
		this.passLast = passLast;
	}

	@Size(min = 5, max = 32)
	@NotBlank
	public String getPassNew() {
		return passNew;
	}

	public void setPassNew(String passNew) {
		this.passNew = passNew;
	}

	@Size(min = 5, max = 32)
	@NotBlank
	public String getPassConf() {
		return passConf;
	}

	public void setPassConf(String passConf) {
		this.passConf = passConf;
	}

}
