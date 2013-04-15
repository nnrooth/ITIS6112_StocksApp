package debug;

public class Timer {
	private static boolean started;
	
	private static long startTime;
	private static long stopTime;
		
	public Timer() {
		reset();
	}
		
	public static boolean isStarted() {
		return started;
	}
	
	public static long getRunTime() {
		if (!started) {
			return (stopTime - startTime);
		} else {
			return (System.currentTimeMillis() - startTime);
		}
	}
	
	public static String getFormattedRunTime() {
		long millis = (getRunTime() % 1000);
		long seconds = (getRunTime() / 1000);
		
		return String.format("[+] Runtime: %s secs %s millis\n", seconds, millis);
	}
	
	public static long getStartTime() {
		return startTime;
	}
	
	public static long getStopTime() {
		return stopTime;
	}
	
	public static void start() {
		if (!started) {
			startTime = System.currentTimeMillis();
			started = true;
		}
	}
	
	public static void stop() {
		if (started) {
			stopTime = System.currentTimeMillis();
			started = false;
		}
	}
	
	public static void reset() {
		started = false;
		startTime = 0;
		stopTime = 0;
	}
}
