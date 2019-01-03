package graphas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.neovisionaries.i18n.CountryCode;

@Component
public class DataLoader implements CommandLineRunner {

	@Autowired
	private GraphAsService graphAsService;

	@Override
	public void run(String... strings) throws Exception {

		graphAsService.save(new ASInfo(1, "PRVI", "TEST", CountryCode.RS));
		graphAsService.save(new ASInfo(2, "drugi", "TEST", CountryCode.BA));
		graphAsService.save(new ASInfo(3, "treci", "TEST", CountryCode.ME));

	}
}
