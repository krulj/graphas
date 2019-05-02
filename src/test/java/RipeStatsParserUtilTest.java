import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;

import graphas.model.ASInfo;
import graphas.util.DelegatedFileParserUtil;

public class RipeStatsParserUtilTest {

	@Test
	public void test1() throws IOException, URISyntaxException {
		List<ASInfo> lines = DelegatedFileParserUtil.getDataFromResource();
		System.out.println(lines);
	}

}
