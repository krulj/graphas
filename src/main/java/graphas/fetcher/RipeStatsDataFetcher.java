package graphas.fetcher;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import graphas.model.AsConnection;
import graphas.model.AsProperties;

public class RipeStatsDataFetcher {

	private static final String REST_API_OVERVIEW = "https://stat.ripe.net/data/as-overview/data.json?resource=AS";
	private static final String REST_ROUTING_STATUS = "https://stat.ripe.net/data/routing-status/data.json?resource=AS";
	private static final String REST_API_NEIGHBOURS = "https://stat.ripe.net/data/asn-neighbours/data.json?resource=AS";

	public static AsProperties getASProperties(Long asNumber) {

		final String name = getAsName(asNumber);
		final String description = getAsDescription(asNumber);

		return new AsProperties(name, description);
	}

	private static String getAsDescription(Long asNumber) {
		RestTemplate restTemplate = new RestTemplate();
		String url = REST_ROUTING_STATUS + asNumber;

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		JSONObject obj = new JSONObject(response.getBody());
		JSONObject jsonData = obj.getJSONObject("data");

		JSONObject space = jsonData.getJSONObject("announced_space");
		JSONObject v4 = space.getJSONObject("v4");
		int v4Prefixes = v4.getInt(("prefixes"));
		String ipv4Originated = "Originated IPv4 prefixes: " + v4Prefixes;

		JSONObject v6 = space.getJSONObject("v6");
		int v6Prefixes = v6.getInt(("prefixes"));
		String ipv6Originated = "Originated IPv6 prefixes: " + v6Prefixes;

		JSONObject jsonBlock = jsonData.getJSONObject("first_seen");
		String prefix = jsonBlock.getString("prefix");
		String time = jsonBlock.getString("time").split("T")[0];
		String announcing = "First ever seen as origin announcing " + prefix + ", on " + time;

		final String description = announcing + "; " + ipv4Originated + "; " + ipv6Originated;
		return description;
	}

	private static String getAsName(Long asNumber) {
		RestTemplate restTemplate = new RestTemplate();
		String url = REST_API_OVERVIEW + asNumber;

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		JSONObject obj = new JSONObject(response.getBody());
		JSONObject jsonData = obj.getJSONObject("data");

		String name = jsonData.getString("holder");
		return name;
	}

	public static List<AsConnection> getASNeighbours(Long asNumber) {
		List<AsConnection> neighboursList = new ArrayList<>();

		RestTemplate restTemplate = new RestTemplate();
		String url = REST_API_NEIGHBOURS + asNumber;

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		JSONObject obj = new JSONObject(response.getBody());

		JSONObject jsonData = obj.getJSONObject("data");

		JSONArray neighboursArray = jsonData.getJSONArray("neighbours");
		for (Object object : neighboursArray) {
			String type = ((JSONObject) object).getString("type");
			int power = ((JSONObject) object).getInt("power");
			long neighbourAsn = ((JSONObject) object).getLong("asn");
			AsConnection asc = new AsConnection(asNumber, neighbourAsn, type, power);
			neighboursList.add(asc);
		}
		return neighboursList;
	}

}
