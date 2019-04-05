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
