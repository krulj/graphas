package graphas;

import java.util.List;

import graphas.model.ASInfo;
import graphas.model.AsProperties;

public interface GraphAsService {

	List<ASInfo> getAll();

	ASInfo getById(Long id);

	ASInfo getByASNumber(Long asNumber);

	List<ASInfo> getByCountry(String country);

	ASInfo save(ASInfo asInfo);

	List<ASInfo> saveAll(List<ASInfo> asInfo);

	List<AsProperties> getAllProperties();

	AsProperties getByName(String name);

}
