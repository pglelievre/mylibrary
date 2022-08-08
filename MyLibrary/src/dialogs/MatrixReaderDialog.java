package dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/** A modal dialog for reading a matrix file with nrow on first line, ncol on second, then all values in column-major.
 * @author Peter
 */
public final class MatrixReaderDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private final MatrixReader matrixReader;
    
    public MatrixReaderDialog(JFrame frame, String title, File file) {
        
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
                    buttonCancel.setEnabled(false); // signals to the MatrixReader to cancel the task
                }
            }
        );

//        // If closed it should behave as the cancel button:
//        addWindowListener(
//            new WindowAdapter() {
//                @Override
//                public void windowClosing(WindowEvent e) {
//                    buttonCancel.setEnabled(false);
//                }
//            }
//        );
        
        // Add all the GUI objects to the dialog window:
        setLayout(new BorderLayout());
        add(scrollPane,BorderLayout.CENTER);
        add(buttonCancel,BorderLayout.SOUTH);
        
        // Must start execution before setting visible!
        matrixReader = new MatrixReader(file,textArea,buttonCancel);
        matrixReader.addPropertyChangeListener( new SwingWorkerCompletionWaiter(this) );
        matrixReader.execute();
        
        // Must set visible after starting execution!
        setVisible(true);
        
    }

    public String getMessage() { return matrixReader.getMessage(); }
    public double[][] getData() { return matrixReader.getData(); }
        
}

