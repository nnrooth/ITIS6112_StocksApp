package utils;

/**
 * This class is used to represent a timeclock object
 * 
 * @author Team 3+4
 * @category Debug Utility
 *
 */
public class Timer {
	private boolean started;
	
	private long startTime;
	private long stopTime;
		
	/**
	 * Default constructor
	 */
	public Timer() {
		reset();
	}
		
	/**
	 * This method checks the current state of the timer.
	 * If the timer is running, it returns true.
	 * If the timer is not running, it returns false.
	 * 
	 * @return true when timer is currently running, else false
	 */
	public boolean isStarted() {
		return started;
	}
	
	/**
	 * Returns the different between the start time and the stop time
	 * 
	 * @return Difference between start and stop times
	 */
	public long getRunTime() {
		if (!started) {
			return (stopTime - startTime);
		} else {
			return (System.currentTimeMillis() - startTime);
		}
	}
	
	/**
	 * The method formats the run time and returns it as a string
	 * 
	 * @return
	 */
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
