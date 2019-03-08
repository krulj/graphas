package graphas.model;

import java.io.Serializable;

public class Node implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private String label;
	private String color = "#e04141";

	public Node(long id, String label, String color) {
		super();
		this.id = id;
		this.label = label;
		this.color = color;
	}

	public Node(long id, String label) {
		super();
		this.id = id;
		this.label = label;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", label=" + label + ", color=" + color + "]";
	}

}
