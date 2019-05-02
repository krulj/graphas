package graphas.fetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import graphas.model.ASInfo;
import graphas.model.AsConnection;

public class ConcurentFetcher {

	private static final Logger logger = Logger.getLogger(ConcurentFetcher.class);
	private ExecutorService executor = Executors.newFixedThreadPool(10);
	private CompletionService<List<AsConnection>> taskCompletionService = new ExecutorCompletionService<>(executor);
	private List<ASInfo> asNumbers;

	public ConcurentFetcher(List<ASInfo> notInDatabase) {
		this.asNumbers = notInDatabase;
	}

	public List<AsConnection> getConnections() {
		List<AsConnection> asConnections = new ArrayList<>();
		try {
			for (ASInfo asNumber : asNumbers) {
				taskCompletionService.submit(new CallableObject(asNumber));
			}
			for (int i = 0; i < asNumbers.size(); i++) {
				Future<List<AsConnection>> completed = taskCompletionService.take();
				asConnections.addAll(completed.get());
			}
		} catch (Exception e) {
			logger.error(e);
		}
		executor.shutdown();
		return asConnections;
	}

}

class CallableObject implements Callable<List<AsConnection>> {
	private static final Logger logger = Logger.getLogger(CallableObject.class);

	private ASInfo asInfo;

	public CallableObject(ASInfo asInfo) {
		this.asInfo = asInfo;
	}

	@Override
	public List<AsConnection> call() throws Exception {
		try {
			switch (asInfo.getrir()) {
			case AFRINIC:	//There is no valid sate for this kind of stats
			case APNIC:
			case ARIN:
			case LACNIC:
			case RIPE:
				return RipeStatsDataFetcher.getASNeighbours(asInfo.getNumber());
			default:
				break;
			}

		} catch (Exception e) {
			logger.error(e);
		}
		return new ArrayList<>();
	}

}
