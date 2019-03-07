package graphas.jobs;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import graphas.model.AsConnection;
import graphas.model.AsProperties;

public class ASPropertiesPopulationJob {

	private static final String REST_API_OVERVIEW = "https://stat.ripe.net/data/as-overview/data.json?resource=AS";
	private static final String REST_API_NEIGHBOURS = "https://stat.ripe.net/data/asn-neighbours/data.json?resource=AS";

	public static AsProperties getASProperties(Long asNumber) {

		RestTemplate restTemplate = new RestTemplate();
		String url = REST_API_OVERVIEW + asNumber;

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		JSONObject obj = new JSONObject(response.getBody());

		JSONObject jsonData = obj.getJSONObject("data");
		String name = jsonData.getString("holder");

		JSONObject jsonBlock = jsonData.getJSONObject("block");
		String resource = jsonBlock.getString("resource");
		String resourceName = jsonBlock.getString("name");
		String resourceDesc = jsonBlock.getString("desc");
		String description = "Resource: " + resource + "; Registry: " + resourceName + "; " + resourceDesc;

		return new AsProperties(name, description);
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
