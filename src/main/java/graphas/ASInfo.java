package graphas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.neovisionaries.i18n.CountryCode;

@Entity
@Table(name = "asinfo")
public class ASInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private long number;
	private CountryCode country;

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

	@Override
	public String toString() {
		return "ASInfo [id=" + id + ", number=" + number + ", country=" + country + "]";
	}

}
