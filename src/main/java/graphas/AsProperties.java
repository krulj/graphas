package graphas;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "asproperties")
public class AsProperties {

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private ASInfo asinfo;

	public AsProperties() {

	}

	public AsProperties(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public ASInfo getAsinfo() {
		return asinfo;
	}

	public void setAsinfo(ASInfo asinfo) {
		this.asinfo = asinfo;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

}
