package graphas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import graphas.exception.ASNotFoundException;

@RestController
class ASInfoController {

	@Autowired
	private final ASInfoRepository repository;

	ASInfoController(ASInfoRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/asinfos")
	List<ASInfo> all() {
		return repository.findAll();
	}

	@GetMapping("/asinfos/{id}")
	ASInfo getOne(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(() -> new ASNotFoundException(id));
	}

	@GetMapping("/asnumber/{id}")
	ASInfo getbyNumber(@PathVariable Long id) {
		return repository.getByAsNumber(id).orElseThrow(() -> new ASNotFoundException(id));
	}

}
