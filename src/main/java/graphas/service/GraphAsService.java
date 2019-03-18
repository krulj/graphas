package graphas.service;

import java.util.List;

import graphas.model.ASInfo;
import graphas.model.AsConnection;
import graphas.model.AsProperties;
import graphas.model.client.CountryStats;
import graphas.model.client.Graph;

public interface GraphAsService {

	List<ASInfo> getAll();

	ASInfo getById(Long id);

	ASInfo getByASNumber(Long asNumber);

	List<ASInfo> getByCountry(String country);

	ASInfo save(ASInfo asInfo);

	List<ASInfo> saveAll(List<ASInfo> asInfo);

	List<AsProperties> getAllProperties();

	AsProperties getByName(String name);

	List<String> getAllCountries();

	List<AsConnection> getConnections(long asNumber);

	List<AsConnection> getConnectionsbyCountry(String country);

	Graph getGraphConnections(Long id);

	Graph getCountryGraphConnections(String country);

	List<ASInfo> getByAsNumbers(List<Long> asn);

	CountryStats getCountryStats(String country);

}
