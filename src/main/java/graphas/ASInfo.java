package graphas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.neovisionaries.i18n.CountryCode;

@Entity
@Table(name = "asinfo")
class ASInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private long number;
	private String name;
	private String description;
	private CountryCode country;

	public ASInfo() {
		// No arg default constructor for Hibernate serialization
	}

	// TODO: Generate equals and hashcode

	public ASInfo(long number, String name, String description, CountryCode conutry) {
		this.number = number;
		this.name = name;
		this.description = description;
		this.country = conutry;
	}

	public Long getId() {
		return id;
	}

	public long getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public CountryCode getCountry() {
		return country;
	}

}
