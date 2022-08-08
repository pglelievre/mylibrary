package dialogs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/** SwingWorker for running an external command.
 * Output is sent to a JTextArea object.
 * @author Peter
 * https://www.oracle.com/technical-resources/articles/javase/swingworker.html
 */
public class CommandRunner extends SwingWorker<String, Void> {
    
    private final String command;
    private final JTextArea textArea;
    private final JButton buttonCancel;
    private String message;
    
    public CommandRunner(String command, JTextArea textArea, JButton buttonCancel) {
        this.command = command;
        this.textArea = textArea;
        this.buttonCancel = buttonCancel;
    }
    
    public String getMessage() { return message; }
    
    // The doInBackground() method defines what we want to do in the background.
    // Any value returned by doInBackground() can be received inside the done() using get().
    @Override
    protected String doInBackground() throws Exception {
        
        textArea.append("> "+command+"\n"); // for reasons I don't entirely understand, this is very important or Dialog to show progress
        
        try {
            
            // Run the command in a process which outputs to the textArea:
            // https://www.researchgate.net/post/print_the_output_of_a_processbuilder_in_a_java_TextArea_during_its_execution
            String outputLineFromCommand;
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
            //BufferedReader errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((outputLineFromCommand = inputStream.readLine()) != null) {
                // Check if user cancelled:
                if (!buttonCancel.isEnabled()) {
                    process.destroy();
                    return "User cancelled.";
                }
                //out.println(outputLineFromCommand);
                textArea.append(outputLineFromCommand+"\n");
            }
            inputStream.close();
            //while ((outputLineFromCommand = errorStream.readLine()) != null) {
            //    //out.println(outputLineFromCommand);
            //    textArea.append(outputLineFromCommand+"\n");
            //}
            //errorStream.close();
            process.waitFor();
            
            return null;
            
        } catch (Exception ex) {
            return "Execution failed.";
        }
        
    }
    
    // The done() method is called when the doInBackground() method finishes. 
    // Any value returned by doInBackground() can be received inside the done() using get().
    @Override
    protected void done() {
        try {
            message = get();
        } catch (Exception ex) {
            message = "Unexpected CommandRunner error: please inform developers.";
        }
    }

}