package graphas.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.neovisionaries.i18n.CountryCode;

import graphas.model.ASInfo;
import graphas.model.RIR;

public class DelegatedFileParserUtil {

	public static List<ASInfo> getDataFromResource() throws IOException, URISyntaxException {
		// Get file from resources folder
		ClassLoader classLoader = DelegatedFileParserUtil.class.getClassLoader();
		List<ASInfo> result = new ArrayList<>();

		for (RIR rir : RIR.values()) {
			String fileName = rir.getFilename();
			Path path = Paths.get(classLoader.getResource(fileName).toURI());
			try (Stream<String> resource = Files.lines(path)) {
				List<ASInfo> list = resource.map(i -> parseLine(rir, i)).filter(Objects::nonNull)
						.collect(Collectors.toList());
				result.addAll(list);
			}
		}

		return result;
	}

	/**
	 * Example of line in file <br>
	 * // ripencc|RU|asn|13056|1|20000303|allocated
	 * 
	 * @param line
	 */
	private static ASInfo parseLine(final RIR rir, final String line) {

		if (line.contains("|asn|")) {
			String patternString = "\\|([A-Z]{2})\\|asn\\|(\\d+)\\|";
			Pattern pattern = Pattern.compile(patternString);
			Matcher matcher = pattern.matcher(line);

			if (matcher.find()) {
				String country = matcher.group(1);
				String numberString = matcher.group(2);

				long number = Long.parseLong(numberString);
				return new ASInfo(number, CountryCode.getByAlpha2Code(country), rir);
			}
		}
		return null;
	}
}
