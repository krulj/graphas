package graphas.model;

import java.io.Serializable;
import java.util.List;

public class Graph implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Node> nodes;
	private List<Edge> edges;

	public Graph(List<Node> nodes, List<Edge> edge) {
		this.nodes = nodes;
		this.edges = edge;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edge) {
		this.edges = edge;
	}

	@Override
	public String toString() {
		return "Graph [nodes=" + nodes + ", edge=" + edges + "]";
	}

}
