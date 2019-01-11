package graphas.jobs;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import graphas.ASInfo;
import graphas.ASproperties;
import graphas.GraphAsService;

public class ASPropertiesPopulationJob {
	
	@Autowired
	private GraphAsService graphAsService;

	private static final String REST_API_RIPE = "https://stat.ripe.net/data/as-overview/data.json?resource=AS";

	public static ASproperties getASProperties(Long asNumber) {

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(REST_API_RIPE + asNumber);
		JSONObject response = target.request(MediaType.APPLICATION_JSON).get(JSONObject.class);

		JSONObject data = response.getJSONObject("data");
		String name = data.getString("holder");
		String desc = data.getString("announced");

		client.close();
		return new ASproperties(name, desc);

	}
	
	public void populateDatabase() {
		List<ASInfo> asInfos = graphAsService.getByCountry("RS");
		for (ASInfo asInfo : asInfos) {
			System.out.println(getASProperties(asInfo.getNumber()));
		}
	}
}
