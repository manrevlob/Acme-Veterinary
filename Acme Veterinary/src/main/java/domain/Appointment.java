package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "startTime"), @Index(columnList = "endTime") })
public class Appointment extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Appointment() {
		super();
	}

	// Identification ---------------------------------------------------------

	private Date moment;
	private String reason;
	private Date day;
	private String startTime;
	private String endTime;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}


	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	// RelationsShips --------------------------------------------------------

	private Veterinary veterinary;
	private Payment payment;
	private History history;
	private Pet pet;

	@Valid
	@ManyToOne(optional = false)
	public Veterinary getVeterinary() {
		return veterinary;
	}

	public void setVeterinary(Veterinary veterinary) {
		this.veterinary = veterinary;
	}

	@Valid
	@OneToOne(optional = true)
	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	public History getHistory() {
		return history;
	}

	public void setHistory(History history) {
		this.history = history;
	}

	@Valid
	@ManyToOne(optional = false)
	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	@Override
	public String toString() {
		return "Appointment [moment=" + moment + ", reason=" + reason
				+ ", veterinary=" + veterinary + ", payment=" + payment
				+ ", history=" + history + ", pet=" + pet + "]";
	}

}
