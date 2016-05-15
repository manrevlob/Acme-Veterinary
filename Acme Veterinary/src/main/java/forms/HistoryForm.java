package forms;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Appointment;

@Access(AccessType.PROPERTY)
public class HistoryForm {

	private String diagnosis;
	private Appointment appointment;
	private String treatmentDescription;
	private Date treatmentStartMoment;
	private Date treatmentEndMoment;

	@NotBlank
	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	@NotNull
	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public String getTreatmentDescription() {
		return treatmentDescription;
	}

	public void setTreatmentDescription(String treatmentDescription) {
		this.treatmentDescription = treatmentDescription;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getTreatmentStartMoment() {
		return treatmentStartMoment;
	}

	public void setTreatmentStartMoment(Date treatmentStartMoment) {
		this.treatmentStartMoment = treatmentStartMoment;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getTreatmentEndMoment() {
		return treatmentEndMoment;
	}

	public void setTreatmentEndMoment(Date treatmentEndMoment) {
		this.treatmentEndMoment = treatmentEndMoment;
	}

}
