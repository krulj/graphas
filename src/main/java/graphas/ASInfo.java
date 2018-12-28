package graphas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.neovisionaries.i18n.CountryCode;

@Entity
@Table(name = "asinfo")
class ASInfo {

	private @Id @GeneratedValue Long id;
	private long number;
	private String name;
	private String description;
	private CountryCode conutry;

	public ASInfo(long number, String name, String description, CountryCode conutry) {
		super();
		this.number = number;
		this.name = name;
		this.description = description;
		this.conutry = conutry;
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

	public CountryCode getConutry() {
		return conutry;
	}

}
