package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard {

	// Constructors -----------------------------------------------------------
	
	public CreditCard() {
		super();
	}

	// Identification ---------------------------------------------------------

	private String name;
	private String brand;
	private String number;
	private Integer expirationMonth;
	private Integer expirationYear;
	private Integer cvv;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	@NotBlank
	@CreditCardNumber
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@NotNull
	@Range(min = 1, max = 12)
	public Integer getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	
	@NotNull
	@Range(min = 2016, max = 9999)
	public Integer getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(Integer expirationYear) {
		this.expirationYear = expirationYear;
	}

	@NotNull
	@Range(min = 0, max = 999)
	public Integer getCvv() {
		return cvv;
	}

	public void setCvv(Integer cvv) {
		this.cvv = cvv;
	}

	@Override
	public String toString() {
		return "CreditCard [name=" + name + ", brand=" + brand + ", number="
				+ number + ", expirationMonth=" + expirationMonth
				+ ", expirationYear=" + expirationYear + ", cvv=" + cvv + "]";
	}

}
