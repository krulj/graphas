package graphas.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.neovisionaries.i18n.CountryCode;

import graphas.ASInfo;

public class RipeStatsParserUtil {

	public static List<ASInfo> getDataFromResource() throws IOException, URISyntaxException {
		// Get file from resources folder
		ClassLoader classLoader = RipeStatsParserUtil.class.getClassLoader();

		Stream<String> stream = Files.lines(Paths.get(classLoader.getResource("delegated-ripencc.txt").toURI()));
		List<ASInfo> list = stream.map(line -> parseLine(line)).filter(Objects::nonNull).collect(Collectors.toList());

		stream.close();

		return list;
	}

	/**
	 * Example of line in file <br>
	 * // ripencc|RU|asn|13056|1|20000303|allocated
	 * 
	 * @param line
	 */
	private static ASInfo parseLine(final String line) {

		if (line.contains("|asn|")) {
			String patternString = "\\|([A-Z]{2})\\|asn\\|(\\d+)\\|";
			Pattern pattern = Pattern.compile(patternString);
			Matcher matcher = pattern.matcher(line);

			if (matcher.find()) {
				String country = matcher.group(1);
				String numberString = matcher.group(2);
				System.out.println(country);
				System.out.println(numberString);

				long number = Long.parseLong(numberString);
				return new ASInfo(number, CountryCode.getByAlpha2Code(country));
			}
		}
		return null;
	}
}
