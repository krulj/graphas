package graphas.jobs;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import graphas.AsProperties;

public class ASPropertiesPopulationJob {

	private static final String REST_API_RIPE = "https://stat.ripe.net/data/as-overview/data.json?resource=AS";

	public static AsProperties getASProperties(Long asNumber) {

		RestTemplate restTemplate = new RestTemplate();
		String url = REST_API_RIPE + asNumber;

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

}
