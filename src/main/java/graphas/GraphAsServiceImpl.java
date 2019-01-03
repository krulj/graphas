package graphas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import graphas.exception.ASNotFoundException;

@Service
public class GraphAsServiceImpl implements GraphAsService {

	@Autowired
	private ASInfoRepository repository;

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

}
