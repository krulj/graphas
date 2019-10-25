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
	private RIR rir;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "asinfo")
	AsProperties asProperties;

	public ASInfo() {
		// No arg default constructor for Hibernate serialization
	}

	// TODO: Generate equals and hashcode

	public ASInfo(long number, CountryCode country, RIR rir) {
		this.number = number;
		this.country = country;
		this.rir = rir;
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

	public RIR getrir() {
		return rir;
	}

	public void setrir(RIR rir) {
		this.rir = rir;
	}

	@Override
	public String toString() {
		return "ASInfo [number=" + number + ", country=" + country + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + (int) (number ^ (number >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ASInfo))
			return false;
		ASInfo other = (ASInfo) obj;
		if (country != other.country)
			return false;
		if (number != other.number)
			return false;
		return true;
	}

	
}
