package graphas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import graphas.model.ASInfo;
import graphas.model.AsConnection;
import graphas.model.client.CountryStats;
import graphas.model.client.Graph;
import graphas.service.GraphAsService;

@RestController
@RequestMapping(value = "api")
class GraphAsRestController {

	@Autowired
	private GraphAsService graphAsService;

	@GetMapping("/asinfos")
	List<ASInfo> all() {
		return graphAsService.getAll();
	}

	@GetMapping("/asnumber/{id}")
	ASInfo getbyNumber(@PathVariable Long id) {
		return graphAsService.getByASNumber(id);
	}
	
	@GetMapping("/countries")
	List<String> allCountries() {
		return graphAsService.getAllCountries();
	}
	
	@GetMapping("/country/{country}")
	List<ASInfo> getbyCountry(@PathVariable String country) {
		return graphAsService.getByCountry(country);
	}
	
	@GetMapping("/countryStats/{country}")
	CountryStats getCountryStats(@PathVariable String country) {
		return graphAsService.getCountryStats(country);
	}
	
	@GetMapping("/country-connections/{country}")
	List<AsConnection> getConnectionsbyCountry(@PathVariable String country) {
		return graphAsService.getConnectionsbyCountry(country);
	}
	
	@GetMapping("/as-connections/{id}")
	List<AsConnection> getConnections(@PathVariable Long id) {
		return graphAsService.getConnections(id);
	}
	
	@GetMapping("/as-connections-graph/{id}")
	Graph getAsGraphConnections(@PathVariable Long id) {
		return graphAsService.getGraphConnections(id);
	}
	
	@GetMapping("/country-connections-graph/{country}")
	Graph getCountryGraphConnections(@PathVariable String country) {
//		List<Node> nodes = new ArrayList<Node>();
//		List<Edge> edges = new ArrayList<Edge>();
//		nodes.add(new Node(6700, "AS6700"));
//		return new Graph(nodes,edges);
		// TODO:
		return graphAsService.getCountryGraphConnections(country);
	}

}
