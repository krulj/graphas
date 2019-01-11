import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.Test;

public class ParserTest {

	@Test
	public void test1() throws IOException, URISyntaxException {
		ClassLoader classLoader = getClass().getClassLoader();

		Stream<String> stream = Files.lines(Paths.get(classLoader.getResource("delegated-ripencc.txt").toURI()));
		stream.forEach(line -> parseLine(line));

		stream.close();
	}

	private void parseLine(String line) {
		// ripencc|RU|asn|13056|1|20000303|allocated
		if (line.contains("|asn|")) {

			String patternString = "\\|([A-Z]{2})\\|asn\\|(\\d+)\\|";
			Pattern pattern = Pattern.compile(patternString);
			Matcher matcher = pattern.matcher(line);

			if (matcher.find()) {
				String country = matcher.group(1);
				String numberString = matcher.group(2);
				System.out.println(country);
				System.out.println(numberString);
				/*
				 * long number = Long.parseLong(numberString);
				 * 
				 * ASInfo record = new ASInfo(number, "", "",
				 * CountryCode.getByAlpha2Code(country));
				 * 
				 * graphAsService.save(record);
				 */
			}

		}
	}

}
