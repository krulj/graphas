package graphas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "asconnection")
public class AsConnection implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "asfrom")
	private long from;
	@Column(name = "asto")
	private long to;
	@Column(name = "conntype")
	private String type;
	private long power;

	public AsConnection() {
		// No arg const for Hibernate serialization
	}

	public AsConnection(long from, long to) {
		this.from = from;
		this.to = to;
	}

	public AsConnection(long from, long to, String type, long power) {
		this.from = from;
		this.to = to;
		this.type = type;
		this.power = power;
	}

	public long getFrom() {
		return from;
	}

	public long getTo() {
		return to;
	}

	public String getType() {
		return type;
	}

	public long getPower() {
		return power;
	}

}
