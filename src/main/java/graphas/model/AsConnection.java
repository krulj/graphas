package graphas.model;

public class AsConnection {

	private long from;
	private long to;
	private String type;
	private long power;

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
