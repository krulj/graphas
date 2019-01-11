package graphas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "asproperties")
public class ASproperties {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;

	@OneToOne
	private ASInfo asinfo;

	public ASproperties() {

	}

	public ASproperties(String name, String description) {
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
