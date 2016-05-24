package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import domain.Pet;
import domain.Veterinary;

@Access(AccessType.PROPERTY)
public class AppointmentForm {

	private String startMoment;
	private String startTime;
	private String endTime;
	private String reason;

	@NotBlank
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	// Relationships
	private Veterinary veterinary;
	private Pet pet;

	@Valid
	@NotNull
	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	@Valid
	@NotNull
	public Veterinary getVeterinary() {
		return veterinary;
	}

	public void setVeterinary(Veterinary veterinary) {
		this.veterinary = veterinary;
	}

	@SafeHtml
	@NotBlank
	public String getStartMoment() {
		return startMoment;
	}

	public void setStartMoment(String startMoment) {
		this.startMoment = startMoment;
	}

	@SafeHtml
	@NotBlank
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@SafeHtml
	@NotBlank
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
