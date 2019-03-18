package graphas.model.client;

import java.io.Serializable;

public class Edge implements Serializable {

	private static final long serialVersionUID = 1L;
	private long from;
	private long to;

	public Edge(long from, long to) {
		super();
		this.from = from;
		this.to = to;
	}

	public long getFrom() {
		return from;
	}

	public void setFrom(long from) {
		this.from = from;
	}

	public long getTo() {
		return to;
	}

	public void setTo(long to) {
		this.to = to;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long calc = from + to;
		result = prime * result + (int) (calc ^ (calc >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (from != other.from && from != other.to)
			return false;
		if (to != other.to && to != other.from)
			return false;
		return true;
	}

}
