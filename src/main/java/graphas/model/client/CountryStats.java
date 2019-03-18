package graphas.model.client;

import java.io.Serializable;
import java.util.List;

public class CountryStats implements Serializable {

	private static final long serialVersionUID = 1L;
	private String country;
	private List<String> asNums;

	public CountryStats(String country, List<String> asNums) {
		super();
		this.country = country;
		this.asNums = asNums;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<String> getAsNums() {
		return asNums;
	}

	public void setAsNums(List<String> asNums) {
		this.asNums = asNums;
	}

}
