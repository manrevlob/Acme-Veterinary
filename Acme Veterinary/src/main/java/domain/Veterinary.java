package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Veterinary extends Actor {

	// Constructors -----------------------------------------------------------

	public Veterinary() {
		super();
	}

	// Identification ---------------------------------------------------------

	// RelationsShips --------------------------------------------------------

	private Collection<History> histories;
	private Clinic clinic;
	private Collection<Comment> comments;

	@Valid
	@OneToMany(mappedBy = "veterinary")
	public Collection<History> getHistories() {
		return histories;
	}

	public void setHistories(Collection<History> histories) {
		this.histories = histories;
	}

	@Valid
	@ManyToOne(optional = false)
	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	@Valid
	@OneToMany(mappedBy = "veterinary")
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

}
