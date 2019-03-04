package graphas;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neovisionaries.i18n.CountryCode;

import graphas.exception.ASNotFoundException;
import graphas.jobs.ASPropertiesPopulationJob;
import graphas.model.ASInfo;
import graphas.model.AsProperties;

@Service
public class GraphAsServiceImpl implements GraphAsService {

	@Autowired
	private AsInfoRepository asInfoRepository;

	@Autowired
	private AsPropertiesRepository asPropertiesRepository;

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
		return countries.stream().map(arg -> arg.getAlpha2() + " - " + arg.getName()).sorted().collect(Collectors.toList());
	}

}
