package graphas.util;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.neovisionaries.i18n.CountryCode;

import graphas.model.ASInfo;
import graphas.model.AsConnection;

public class GraphUtils {

	// private List<ASInfo> asInfos;

	public GraphUtils() {
		// TODO Auto-generated constructor stub
	}

	private int getRed(int country) {
		return (77 + country) % 255;
	}

	private int getGreen(int country) {
		return (117 + country) % 255;
	}

	private int getBlue(int country) {
		return (211 + country) % 255;
	}

	// color = "#e04141"
	private String getColorString(CountryCode countryCode) {
		int numeric = countryCode.getNumeric();
		return String.format("#%02x%02x%02x", getRed(numeric), getGreen(numeric), getBlue(numeric));
	}

	public HashMap<Long, String> getColorStringsConnections(List<ASInfo> asInfos) {
		HashMap<Long, String> numberColorMap = new HashMap<Long, String>();
		for (ASInfo asInfo : asInfos) {
			String color = getColorString(asInfo.getCountry());
			numberColorMap.put(asInfo.getNumber(), color);
		}
		return numberColorMap;
	}

	public List<Long> getDistinctNodes(List<AsConnection> asConnections) {
		List<Long> all = asConnections.stream().map(AsConnection::getTo).distinct().collect(Collectors.toList());
		all.addAll(asConnections.stream().map(AsConnection::getFrom).distinct().collect(Collectors.toList()));
		return all;
	}

	public HashMap<Long, CountryCode> getGroup(List<ASInfo> asInfos) {
		HashMap<Long, CountryCode> numberGroupMap = new HashMap<>();
		for (ASInfo asInfo : asInfos) {
			numberGroupMap.put(asInfo.getNumber(), asInfo.getCountry());
		}
		return numberGroupMap;
	}

}
