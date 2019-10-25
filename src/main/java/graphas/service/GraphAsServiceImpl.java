package graphas.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neovisionaries.i18n.CountryCode;

import graphas.AsConnectionRepository;
import graphas.AsInfoRepository;
import graphas.AsPropertiesRepository;
import graphas.exception.ASNotFoundException;
import graphas.fetcher.ArinStatsDataFetcher;
import graphas.fetcher.ConcurentFetcher;
import graphas.fetcher.RipeStatsDataFetcher;
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
	private static final Logger logger = Logger.getLogger(GraphAsServiceImpl.class);

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
			AsProperties properties;
			switch (asInfo.getrir()) {

			case AFRINIC:
			case APNIC:
				// properties = ApnicStatsDataFetcher.getASProperties(asNumber);
				// break;
			case ARIN:
				properties = ArinStatsDataFetcher.getASProperties(asNumber);
				break;
			case LACNIC:
			case RIPE:
				properties = RipeStatsDataFetcher.getASProperties(asNumber);
				break;
			default:
				properties = RipeStatsDataFetcher.getASProperties(asNumber);
				break;
			}

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
		return countries.stream().filter(c -> c != null && c.getName() != null && c.getAlpha3() != null)
				.map(c -> c.getAlpha3() + " - " + c.getName()).sorted().collect(Collectors.toList());
	}

	@Override
	public List<AsConnection> getConnectionsbyCountry(String country) {
		List<ASInfo> asInfos = getByCountry(country);
		List<AsConnection> asConnections = new ArrayList<>();

		List<Long> asNumbers = asInfos.stream().map(ASInfo::getNumber).collect(Collectors.toList());

		// Get all from database if exists
		List<AsConnection> dbConnections = asConnectionRepository.getByAsNumbers(asNumbers);
		asConnections.addAll(dbConnections);

		// Get rest of items from Internet
		List<Long> inDatabase = dbConnections.stream().map(AsConnection::getFrom).distinct()
				.collect(Collectors.toList());
		List<ASInfo> notInDatabase = asInfos.stream().filter(i -> !inDatabase.contains(i.getNumber()))
				.collect(Collectors.toList());

		ConcurentFetcher concurentFetcher = new ConcurentFetcher(notInDatabase);
		List<AsConnection> connections = concurentFetcher.getConnections();

		// Save new connections to database
		for (AsConnection newConnections : connections) {
			asConnectionRepository.save(newConnections);
		}

		asConnections.addAll(connections);
		return asConnections;
	}

	@Override
	public List<AsConnection> getConnections(long asNumber) {
		List<AsConnection> dbConnections = asConnectionRepository.getByAsNumber(asNumber);
		if (dbConnections == null || dbConnections.isEmpty()) {
			List<AsConnection> connections = RipeStatsDataFetcher.getASNeighbours(asNumber);
			for (AsConnection asConnection : connections) {
				asConnectionRepository.save(asConnection);
			}
			return connections;

		}
		return dbConnections;
	}

	@Override
	public Graph getGraphConnections(Long id) {
		logger.info("Get connections for AS" + id + " " + LocalTime.now().toString());
		List<AsConnection> asConnections = getConnections(id);
		logger.info("Connections collected for AS" + id + " " + LocalTime.now().toString());

		List<Node> nodes = getNodes(asConnections);
		List<Edge> edges = getEdges(asConnections);

		logger.info("Parsing connections ended for: " + id + " " + LocalTime.now().toString());

		return new Graph(nodes, edges);
	}

	@Override
	public Graph getCountryGraphConnections(String country) {
		logger.info("Get connections for: " + country + LocalTime.now().toString());
		List<AsConnection> asConnections = getConnectionsbyCountry(country);
		logger.info("Connections collected for: " + country + LocalTime.now().toString());

		List<Node> nodes = getNodes(asConnections);
		List<Edge> edges = getEdges(asConnections);
		logger.info("Parsing connections ended for: " + country + LocalTime.now().toString());

		return new Graph(nodes, edges);
	}

	private List<Edge> getEdges(List<AsConnection> asConnections) {
		Set<Edge> edges = new HashSet<>();

		for (AsConnection asConnection : asConnections) {
			long to = asConnection.getTo();
			long from = asConnection.getFrom();
			long value = asConnection.getPower();
			edges.add(new Edge(from, to, value));
		}
		return new ArrayList<>(edges);
	}

	private List<Node> getNodes(List<AsConnection> asConnections) {
		Set<Node> nodes = new HashSet<>();
		GraphUtils graphUtils = new GraphUtils();
		List<Long> all = graphUtils.getDistinctNodes(asConnections);
		List<ASInfo> asInfos = getByAsNumbers(all);
		for (ASInfo asInfo : asInfos) {
			long number = asInfo.getNumber();
			CountryCode country = asInfo.getCountry();
			nodes.add(new Node(number, "AS" + number, country.getNumeric(), country.getName()));
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
