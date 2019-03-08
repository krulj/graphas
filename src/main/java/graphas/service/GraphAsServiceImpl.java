package graphas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neovisionaries.i18n.CountryCode;

import graphas.AsConnectionRepository;
import graphas.AsInfoRepository;
import graphas.AsPropertiesRepository;
import graphas.exception.ASNotFoundException;
import graphas.jobs.ASPropertiesPopulationJob;
import graphas.model.ASInfo;
import graphas.model.AsConnection;
import graphas.model.AsProperties;

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
		return countries.stream().map(arg -> arg.getAlpha2() + " - " + arg.getName()).sorted()
				.collect(Collectors.toList());
	}

	@Override
	public List<AsConnection> getConnectionsbyCountry(String country) {
		List<ASInfo> asInfos = getByCountry(country);
		List<AsConnection> asConnections = new ArrayList<>();
		for (ASInfo asInfo : asInfos) {
			List<AsConnection> connections = ASPropertiesPopulationJob.getASNeighbours(asInfo.getNumber());
			asConnections.addAll(connections);
		}
		return asConnections;
	}

	@Override
	@Transactional
	public List<AsConnection> getConnections(long asNumber) {
		List<AsConnection> asConnections = asConnectionRepository.getByAsNumber(asNumber);
		if (asConnections == null || asConnections.isEmpty()) {
			List<AsConnection> connections = ASPropertiesPopulationJob.getASNeighbours(asNumber);
			asConnectionRepository.saveAll(asConnections);
			asConnectionRepository.flush();
			return connections;

		}
		return new ArrayList<>();
	}

}
