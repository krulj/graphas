package graphas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neovisionaries.i18n.CountryCode;

import graphas.AsConnectionRepository;
import graphas.AsInfoRepository;
import graphas.AsPropertiesRepository;
import graphas.exception.ASNotFoundException;
import graphas.jobs.ASPropertiesPopulationJob;
import graphas.model.ASInfo;
import graphas.model.AsConnection;
import graphas.model.AsProperties;
import graphas.model.client.CountryStats;
import graphas.model.client.Edge;
import graphas.model.client.Graph;
import graphas.model.client.Node;
import graphas.util.GraphUtils;

@Service
public class GraphAsServiceImpl implements GraphAsService {

	@Autowired
	private AsInfoRepository asInfoRepository;

	@Autowired
	private AsPropertiesRepository asPropertiesRepository;

	@Autowired
	private AsConnectionRepository asConnectionRepository;

	@Override
	public List<ASInfo> getAll() {
		return asInfoRepository.findAll();
	}

	@Override
	public ASInfo getById(Long id) {
		return asInfoRepository.findById(id).orElseThrow(() -> new ASNotFoundException(id));
	}

	@Override
	public ASInfo getByASNumber(Long asNumber) {
		ASInfo asInfo = asInfoRepository.getByAsNumber(asNumber).orElseThrow(() -> new ASNotFoundException(asNumber));
		if (asInfo.getAsProperties() == null) {
			AsProperties properties = ASPropertiesPopulationJob.getASProperties(asNumber);
			asInfo.setAsProperties(properties);
			properties.setAsinfo(asInfo);
			asInfoRepository.save(asInfo);
		}
		return asInfo;
	}

	@Override
	public ASInfo save(ASInfo asInfo) {
		return asInfoRepository.save(asInfo);
	}

	@Override
	public List<ASInfo> getByCountry(String country) {
		return asInfoRepository.getByCountry(CountryCode.getByAlpha3Code(country));
	}

	@Override
	public List<ASInfo> saveAll(List<ASInfo> asInfo) {
		return asInfoRepository.saveAll(asInfo);
	}

	@Override
	public List<AsProperties> getAllProperties() {
		return asPropertiesRepository.findAll();
	}

	@Override
	public AsProperties getByName(String name) {
		return asPropertiesRepository.getByName(name).orElseThrow(() -> new ASNotFoundException(name));
	}

	@Override
	public List<String> getAllCountries() {
		List<CountryCode> countries = asInfoRepository.getAllCountries();
		return countries.stream().map(arg -> arg.getAlpha3() + " - " + arg.getName()).sorted()
				.collect(Collectors.toList());
	}

	@Override
	public List<AsConnection> getConnectionsbyCountry(String country) {
		List<ASInfo> asInfos = getByCountry(country);
		List<AsConnection> asConnections = new ArrayList<>();
		for (ASInfo asInfo : asInfos) {
			List<AsConnection> connections = getConnections(asInfo.getNumber());
			asConnections.addAll(connections);
		}
		return asConnections;
	}

	@Override
	public List<AsConnection> getConnections(long asNumber) {
		List<AsConnection> dbConnections = asConnectionRepository.getByAsNumber(asNumber);
		if (dbConnections == null || dbConnections.isEmpty()) {
			List<AsConnection> connections = ASPropertiesPopulationJob.getASNeighbours(asNumber);
			for (AsConnection asConnection : connections) {
				asConnectionRepository.save(asConnection);
			}
			return connections;

		}
		return dbConnections;
	}

	@Override
	public Graph getGraphConnections(Long id) {
		List<AsConnection> asConnections = getConnections(id);

		List<Node> nodes = getNodes(asConnections);
		List<Edge> edges = getEdges(asConnections);

		return new Graph(nodes, edges);
	}

	@Override
	public Graph getCountryGraphConnections(String country) {
		List<AsConnection> asConnections = getConnectionsbyCountry(country);

		List<Node> nodes = getNodes(asConnections);
		List<Edge> edges = getEdges(asConnections);

		return new Graph(nodes, edges);
	}

	private List<Edge> getEdges(List<AsConnection> asConnections) {
		Set<Edge> edges = new HashSet<>();

		for (AsConnection asConnection : asConnections) {
			long to = asConnection.getTo();
			long from = asConnection.getFrom();
			edges.add(new Edge(from, to));
		}
		return new ArrayList<>(edges);
	}

	private List<Node> getNodes(List<AsConnection> asConnections) {
		Set<Node> nodes = new HashSet<>();
		GraphUtils graphUtils = new GraphUtils();
		List<Long> all = graphUtils.getDistinctNodes(asConnections);
		List<ASInfo> asInfos = getByAsNumbers(all);
		HashMap<Long, CountryCode> mapCountryId = graphUtils.getGroup(asInfos);
		for (Long id : all) {
			CountryCode group = mapCountryId.get(id);
			nodes.add(new Node(id, "AS" + id, group.getNumeric(), group.getName()));
		}
		return new ArrayList<>(nodes);
	}

	@Override
	public List<ASInfo> getByAsNumbers(List<Long> asn) {
		return asInfoRepository.getByAsNumbers(asn);
	}

	@Override
	public CountryStats getCountryStats(String country) {
		List<ASInfo> asInfos = getByCountry(country);
		List<String> asNames = asInfos.stream().map(ASInfo::getName).collect(Collectors.toList());
		String name = CountryCode.getByAlpha3Code(country).getName();
		return new CountryStats(name, asNames);
	}

}
