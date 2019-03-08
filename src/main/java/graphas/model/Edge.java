package graphas.model;

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
	public String toString() {
		return "Edges [from=" + from + ", to=" + to + "]";
	}

}
