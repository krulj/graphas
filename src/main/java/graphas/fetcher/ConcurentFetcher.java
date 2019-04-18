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

import graphas.model.AsConnection;

public class ConcurentFetcher {

	private static final Logger logger = Logger.getLogger(ConcurentFetcher.class);
	private ExecutorService executor = Executors.newFixedThreadPool(10);
	private CompletionService<List<AsConnection>> taskCompletionService = new ExecutorCompletionService<>(executor);
	private List<Long> asNumbers;

	public ConcurentFetcher(List<Long> asNumbers) {
		this.asNumbers = asNumbers;
	}

	public List<AsConnection> getConnections() {
		List<AsConnection> asConnections = new ArrayList<>();
		try {
			for (Long asNumber : asNumbers) {
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

	private long asNumber;

	public CallableObject(long asNumber) {
		this.asNumber = asNumber;
	}

	@Override
	public List<AsConnection> call() throws Exception {
		try {
			return RipeStatsDataFetcher.getASNeighbours(asNumber);
		} catch (Exception e) {
			logger.error(e);
		}
		return new ArrayList<>();
	}

}
