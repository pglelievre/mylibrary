package dialogs;

/** Class for estimating the time remaining for an iterative task.
 * @author Peter
 */
public class TimeEstimator {
    private static final long serialVersionUID = 1L;
    
    private float limit; // total number of iterations for the task
    private long timeStart; // time that the task was started (ms)
    private long timeEnd; // time that the task ended (ms)
    private long timeElapsed; // time that the task took (ms)
    
    /** Constructor.
     * @param limit Total number of iterations for the task.
     */
    public TimeEstimator(int limit) {
        this.limit = limit;
    }
    
    /** Starts the timer. */
    public void start() {
        timeStart = System.currentTimeMillis();
    }
    
    /** Returns the estimated time remaining (ms).
     * @param i The current iteration number completed, counting from 0.
     * @return The estimated time remaining (ms).
     */
    public int estimate(int i) {
        int ip1 = i + 1;
        if ( ip1 >= limit ) { return 0; } // overrun limit
        float f = ip1;
        long timeSinceStart = System.currentTimeMillis() - timeStart;
        float timePerIteration = timeSinceStart / f;
        float timeRemaining = timePerIteration*(limit-f); // estimated time remaining
        float secondsRemaining = timeRemaining / 1000;
        return (int)secondsRemaining;
    }
    
    /** Stops the timer.
     * @return The elapsed time for the task (ms);
     */
    public long stop() {
        timeEnd = System.currentTimeMillis();
        timeElapsed = timeEnd - timeStart;
        return timeElapsed;
    }
    
}