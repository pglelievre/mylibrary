package dialogs;

import java.io.PrintStream;

/** Class for printing the percent of an iterative task completed and providing estimated time remaining.
 * @author Peter
 */
public class PercentDone {
    
    private float limit; // total number of iterations for the task
    private float step; // a message is printed to standard output each step percent of the task
    private float show; // the next percentage to show
    private String prefix; // prefix to print to standard output (usually a set of spaces)
    private TimeEstimator timer; // used to calculate estimated time remaining
    
    /** Constructor.
     * @param limit The total number of iterations for the task.
     * @param step ! A message is printed to standard output each step percent of the task.
     * @param prefix ! Prefix to print to standard output (usually a set of spaces).
     */
    public PercentDone(int limit, int step, String prefix) {
        this.limit = limit;
        this.step = step;
        this.show = step;
        this.prefix = prefix;
        timer = new TimeEstimator(limit);
    }
    
    /** Should be called to start the timing process just before the task starts.
     * @return A string to print (contains final \n character).
     */
    public String start() {
        timer.start();
        //System.out.println(prefix + "0% done.");
        return prefix + "0% done.\n";
    }
    
    /** Updates the record of how much of the task has been performed and prints a message to standard output if required.
     * @param i The current iteration number completed, counting from 0.
     * @return A string to print (contains final \n character) or null if there is nothing to print.
     */
    public String update(int i) {
        PrintStream out = System.out;
        int ip1 = i + 1;
        String s = null;
        if ( ip1 >= limit ) {
            s = prefix + "100% done.\n";
        } else {
            float d = 100*ip1/limit;
            // A loop is required in case the progress has skipped a percentage step:
            while(true) {
                if ( d >= show ) {
                    int secondsRemaining = timer.estimate(i);
                    String t = prefix + (int)show + "% done, time remaining = " + secondsRemaining + " seconds.\n";
                    if (s==null) {
                        s = t;
                    } else {
                        s += t;
                    }
                    show += step;
                } else {
                    break;
                }
            }
        }
        //out.println(s);
        return s;
    }
    
    /** Can be used to stop the timing process just after the task ends.
     * @return The elapsed time (ms).
     */
    public long stop() {
        return timer.stop();
    }
    
}