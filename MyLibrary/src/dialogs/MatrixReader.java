package dialogs;

import java.io.File;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/** SwingWorker for reading a matrix file with nrow on first line, ncol on second, then all values in column-major.
 * Output is sent to a JTextArea object.
 * @author Peter
 * https://www.oracle.com/technical-resources/articles/javase/swingworker.html
 */
public class MatrixReader extends SwingWorker<MatrixReaderReturnObject, Void> {
    
    private File file;
    private JTextArea textArea;
    private JButton buttonCancel;
    MatrixReaderReturnObject returnObj;
    
    public MatrixReader(File file, JTextArea textArea, JButton buttonCancel) {
        this.file = file;
        this.textArea = textArea;
        this.buttonCancel = buttonCancel;
    }
    
    public String getMessage() { return returnObj.message; }
    public double[][] getData() { return returnObj.data; }
    
    // The doInBackground() method defines what we want to do in the background.
    // Any value returned by doInBackground() can be received inside the done() using get().
    @Override
    protected MatrixReaderReturnObject doInBackground() throws Exception {
        
        textArea.append("Reading matrix file ...\n");
        
        // Open file for reading:
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (Exception ex) {
            return new MatrixReaderReturnObject("Matrix file not found.");
        }
        int nrow, ncol;
        try {
            nrow = scanner.nextInt();
            ncol = scanner.nextInt();
        } catch (Exception ex) {
            return new MatrixReaderReturnObject("Error reading matrix size information from file.");
        }
        
        // Check size of data array provided:
        //if ( nrow != data.length || ncol != data[0].length ) {
        //    //System.out.println("Unexpected size inconsistency in matrix file.");
        //    textArea.append("Unexpected size inconsistency in matrix file.");
        //    return null;
        //}
        
        // Create new data array of correct size:
        double[][] d = new double[nrow][ncol];
        
        // Read the file and print progress information or errors in the dialog:
        PercentDone percentDone = new PercentDone(ncol,10,"   ");
        String s = percentDone.start();
        textArea.append(s);
        for ( int j=0 ; j<ncol ; j++ ) {
            // Check if user cancelled:
            if (!buttonCancel.isEnabled()) {
                scanner.close();
                return new MatrixReaderReturnObject("User cancelled.");
            }
            // Read all the data:
            for ( int i=0 ; i<nrow ; i++ ) {
                try {
                    d[i][j] = scanner.nextDouble();
                } catch (Exception ex) {
                    return new MatrixReaderReturnObject("Error reading matrix data from file.");
                }
            }
            s = percentDone.update(j);
            if (s!=null) { textArea.append(s); }
            //setProgress( (int)( (j+1)*100/(float)ncol) );
        }
        scanner.close();
        return new MatrixReaderReturnObject(d);
    }
    
    // The done() method is called when the doInBackground() method finishes. 
    // Any value returned by doInBackground() can be received inside the done() using get().
    @Override
    protected void done() {
        try {
            returnObj = get();
        } catch (Exception ex) {
            returnObj = new MatrixReaderReturnObject("Unexpected MatrixReader error: please inform developers.");
        }
    }

}