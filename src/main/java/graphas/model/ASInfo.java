package graphas.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neovisionaries.i18n.CountryCode;

@Entity
@Table(name = "asinfo")
public class ASInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private long number;
	private CountryCode country;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "asinfo")
	AsProperties asProperties;

	public ASInfo() {
		// No arg default constructor for Hibernate serialization
	}

	// TODO: Generate equals and hashcode

	public ASInfo(long number, CountryCode conutry) {
		this.number = number;
		this.country = conutry;
	}

	public Long getId() {
		return id;
	}

	public long getNumber() {
		return number;
	}

	public CountryCode getCountry() {
		return country;
	}

	public AsProperties getAsProperties() {
		return asProperties;
	}

	public void setAsProperties(AsProperties asProperties) {
		this.asProperties = asProperties;
	}

	public String getName() {
		return "AS" + number;
	}

	@Override
	public String toString() {
		return "ASInfo [number=" + number + ", country=" + country + "]";
	}

}
