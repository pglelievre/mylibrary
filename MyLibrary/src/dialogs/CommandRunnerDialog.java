package dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/** A modal dialog for reading a matrix file with nrow on first line, ncol on second, then all values in column-major.
 * @author Peter
 */
public final class CommandRunnerDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    
    private final CommandRunner commandRunner;
    
    public CommandRunnerDialog(JFrame frame, String title, ProcessBuilder processBuilder) {
        
        // Make modal, set title, size:
        super(frame,title,true); // owner, title, modal
        //setModal(true);
        //setTitle(title);
        setSize(400, 400);
        this.setLocationRelativeTo(frame);
        
//        // This is only important for testing:
//        addWindowListener(
//            new WindowAdapter() {
//                @Override
//                public void windowClosing(WindowEvent e) { System.exit(0); }
//            }
//        );

        // Create a text area inside a scroll pane:
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(
            // This listener will make the pane scroll to the end each time its contents change.
            new AdjustmentListener() {
                @Override
                public void adjustmentValueChanged(AdjustmentEvent e) {
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
                }
            }
        );
        
        // Create button to cancel the background task when clicked:
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttonCancel.setEnabled(false); // signals to the CommandRunner to cancel the task
                }
            }
        );
        
        // Add all the GUI objects to the dialog window:
        setLayout(new BorderLayout());
        add(scrollPane,BorderLayout.CENTER);
        add(buttonCancel,BorderLayout.SOUTH);
        
        // Must start execution before setting visible!
        commandRunner = new CommandRunner(processBuilder,textArea,buttonCancel);
        commandRunner.addPropertyChangeListener( new SwingWorkerCompletionWaiter(this) );
        commandRunner.execute();
        
        // Must set visible after starting execution!
        setVisible(true);
        
    }

    public String getMessage() { return commandRunner.getMessage(); }
        
}

