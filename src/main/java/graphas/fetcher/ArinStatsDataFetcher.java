package graphas.fetcher;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import graphas.model.AsProperties;

public class ArinStatsDataFetcher {

	private static final String REST_API_OVERVIEW = "https://rdap.arin.net/registry/autnum/";

	public static AsProperties getASProperties(Long asNumber) {

		RestTemplate restTemplate = new RestTemplate();
		String url = REST_API_OVERVIEW + asNumber;

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		JSONObject obj = new JSONObject(response.getBody());

		String resourceName = obj.getString("name");

		JSONArray entitiesData = obj.getJSONArray("entities");
		JSONObject entity = entitiesData.getJSONObject(0);
		JSONArray vcardData = entity.getJSONArray("vcardArray");

		VCard vcard = Ezvcard.parseJson(vcardData.toString()).first();
		String description = vcard.getFormattedName().getValue();

		return new AsProperties(resourceName, description);
	}

}
