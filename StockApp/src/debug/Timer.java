package debug;

public class Timer {
	private boolean started;
	
	private long startTime;
	private long stopTime;
		
	public Timer() {
		reset();
	}
		
	public boolean isStarted() {
		return started;
	}
	
	public long getRunTime() {
		if (!started) {
			return (stopTime - startTime);
		} else {
			return (System.currentTimeMillis() - startTime);
		}
	}
	
	public String getFormattedRunTime() {
		long millis = (getRunTime() % 1000);
		long seconds = (getRunTime() / 1000);
		
		return String.format("[+] Runtime: %s secs %s millis\n", seconds, millis);
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public long getStopTime() {
		return stopTime;
	}
	
	public void start() {
		if (!started) {
			startTime = System.currentTimeMillis();
			started = true;
		}
	}
	
	public void stop() {
		if (started) {
			stopTime = System.currentTimeMillis();
			started = false;
		}
	}
	
	public void reset() {
		started = false;
		startTime = 0;
		stopTime = 0;
	}
}
