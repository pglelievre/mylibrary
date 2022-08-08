package gui;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JTextArea;

/** This class extends from OutputStream to redirect system output or error messages to a JTextArea.
 * Redirection occurs in the constructor.
 * @author Peter Lelievre.
 * https://www.codejava.net/java-se/swing/redirect-standard-output-streams-to-jtextarea
 */
public class JTextAreaOutputStream extends OutputStream {
    
    private JTextArea textArea;
    private PrintStream printStream;
    
    /** Constructor.
     * @param textArea The JTextArea object to redirect system output or error messages to.
     */
    public JTextAreaOutputStream(JTextArea textArea) {
        this.textArea = textArea;
        printStream = new PrintStream(this);
    }
    
    /** Redirects system output to the JTextArea. */
    public void redirectOut() {
        System.setOut(printStream);
    }
    
    /** Redirects system errors to the JTextArea. */
    public void redirectErr() {
        System.setErr(printStream);
    }
    
    /** Redirects system output and errors to the JTextArea. */
    public void redirect() {
        System.setOut(printStream);
        System.setErr(printStream);
    }
    
    @Override
    public void write(int b) throws IOException {
        // Write data to the text area:
        textArea.append(String.valueOf((char)b)); // reported to cause flashing by a user on stack overflow but I did not see that
        // https://stackoverflow.com/questions/5107629/how-to-redirect-console-content-to-a-textarea-in-java
        //textArea.setText( textArea.getText() + String.valueOf((char)b) );
        // Scrolls the text area to the end of data:
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    
}