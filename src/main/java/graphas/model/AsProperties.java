package graphas.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "asproperties")
public class AsProperties implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;

	@JsonIgnore
	@MapsId
	@OneToOne
	@JoinColumn(name = "id", nullable = false)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asinfo == null) ? 0 : asinfo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AsProperties))
			return false;
		AsProperties other = (AsProperties) obj;
		if (asinfo == null) {
			if (other.asinfo != null)
				return false;
		} else if (!asinfo.equals(other.asinfo))
			return false;
		return true;
	}

	
}
