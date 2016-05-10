package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Clinic extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Clinic() {
		super();
	}

	// Identification ---------------------------------------------------------
	private String name;
	private String address;
	private String zipCode;
	private String pictures;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@NotBlank
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@NotBlank
	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	// Relationships ---------------------------------------------------------

	private Collection<Veterinary> veterinaries;
	private Collection<Bulletin> bulletins;
	private Collection<Appointment> appointments;

	@Valid
	@OneToMany(mappedBy = "clinic")
	public Collection<Veterinary> getVeterinaries() {
		return veterinaries;
	}

	public void setVeterinaries(Collection<Veterinary> veterinaries) {
		this.veterinaries = veterinaries;
	}

	@Valid
	@OneToMany(mappedBy = "clinic")
	public Collection<Bulletin> getBulletins() {
		return bulletins;
	}

	public void setBulletins(Collection<Bulletin> bulletins) {
		this.bulletins = bulletins;
	}

	@Valid
	@OneToMany(mappedBy = "clinic")
	public Collection<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Collection<Appointment> appointments) {
		this.appointments = appointments;
	}

}
