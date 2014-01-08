package at.ac.tuwien.swa.swazam.network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Singleton for ExecutorService
 * @author x.zhang
 *
 */
public final class ExecutorFactory {

	private static ExecutorService executor;

	public static ExecutorService getExectutor() {
		if (executor == null) {
			executor = Executors.newCachedThreadPool();
		}
		
		return executor;
	}
}
