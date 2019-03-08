package graphas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import graphas.model.ASInfo;
import graphas.service.GraphAsService;
import graphas.util.RipeStatsParserUtil;

@Component
public class DataLoader implements CommandLineRunner {

	@Autowired
	private GraphAsService graphAsService;

	@Override
	public void run(String... strings) throws Exception {

		// Get file from resources folder
		List<ASInfo> list = RipeStatsParserUtil.getDataFromResource();
		graphAsService.saveAll(list);

				
	}

}
