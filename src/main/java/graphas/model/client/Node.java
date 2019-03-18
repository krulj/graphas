package graphas.model.client;

import java.io.Serializable;

public class Node implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private String label;
	private long group;
	private String title;

	public Node(long id, String label, long group, String title) {
		this.id = id;
		this.label = label;
		this.group = group;
		this.title = title;
	}

	public Node(long id, String label, long group) {
		this.id = id;
		this.label = label;
		this.group = group;
	}

	public Node(long id, String label) {
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

	public long getGroup() {
		return group;
	}

	public void setGroup(long group) {
		this.group = group;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Node other = (Node) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
