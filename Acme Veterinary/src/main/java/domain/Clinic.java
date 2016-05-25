package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

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
	private Boolean isDeleted;

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Pattern(regexp = "^\\d{5}?$")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	
	// Relationships ---------------------------------------------------------

	private Collection<Veterinary> veterinaries;
	private Collection<Bulletin> bulletins;

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

}
