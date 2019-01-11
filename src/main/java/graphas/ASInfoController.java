package graphas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ASInfoController {

	@Autowired
	private GraphAsService graphAsService;

	@GetMapping("/asinfos")
	List<ASInfo> all() {
		return graphAsService.getAll();
	}

	@GetMapping("/asinfos/{id}")
	ASInfo getOne(@PathVariable Long id) {
		return graphAsService.getById(id);
	}

	@GetMapping("/asnumber/{id}")
	ASInfo getbyNumber(@PathVariable Long id) {
		return graphAsService.getByASNumber(id);
	}
	
	@GetMapping("/country/{country}")
	List<ASInfo> getbyCountry(@PathVariable String country) {
		return graphAsService.getByCountry(country);
	}

}
