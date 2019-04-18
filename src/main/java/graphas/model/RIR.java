package graphas.model;

public enum RIR {

	AFRINIC("delegated-afrinic-latest.txt", "url1"), // Africa Region
	APNIC("delegated-apnic-latest.txt", "url2"), // Asia/Pacific Region
	ARIN("delegated-arin-extended-latest.txt", "url3"), // Canada, USA, and some Caribbean Islands
	LACNIC("delegated-lacnic-extended-latest.txt", "url4"), // Latin America and some Caribbean Islands
	RIPE("delegated-ripencc-latest.txt", "url5"); // Europe, the Middle East, and Central Asia

	private final String filename;
	private final String whoisUrl;

	private RIR(String filename, String whoisUrl) {
		this.filename = filename;
		this.whoisUrl = whoisUrl;
	}

	public String getFilename() {
		return filename;
	}

	public String getWhoisUrl() {
		return whoisUrl;
	}

}
