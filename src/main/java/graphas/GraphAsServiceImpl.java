package graphas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neovisionaries.i18n.CountryCode;

import graphas.exception.ASNotFoundException;
import graphas.jobs.ASPropertiesPopulationJob;

@Service
public class GraphAsServiceImpl implements GraphAsService {

	@Autowired
	private GraphAsRepository repository;

	@Override
	public List<ASInfo> getAll() {
		return repository.findAll();
	}

	@Override
	public ASInfo getById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ASNotFoundException(id));
	}

	@Override
	public ASInfo getByASNumber(Long asNumber) {
		return repository.getByAsNumber(asNumber).orElseThrow(() -> new ASNotFoundException(asNumber));
	}

	@Override
	public ASInfo save(ASInfo asInfo) {
		return repository.save(asInfo);
	}

	@Override
	public List<ASInfo> getByCountry(String country) {
		return repository.getByCountry(CountryCode.getByAlpha2Code(country));
	}

	@Override
	public List<ASInfo> saveAll(List<ASInfo> asInfo) {
		return repository.saveAll(asInfo);
	}

	@Override
	public List<ASproperties> getAllProperties() {
		List<ASInfo> asInfos = getByCountry("RS");
		for (ASInfo asInfo : asInfos) {
			System.out.println(ASPropertiesPopulationJob.getASProperties(asInfo.getNumber()));
		}
		return null;
	}

}
