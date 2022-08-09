package dialogs;

// Importing required classes
import dialogs.CommandRunnerDialog;
import dialogs.MatrixReaderDialog;
import dialogs.Dialogs;
import java.io.File;
import javax.swing.*;

/** SwingWorker example.
 * @author Peter Lelievre.
 */
public class SwingWorkerTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
            new Runnable() {
                @Override
                public void run() {
                    
                    //File file = new File("/Users/Peter/Work/Temp/junk_big_file.txt");
                    //TestDialog dialog = new TestDialog("SwingWorker Test",file);
                    
                    String title;
                    String message;
                    
                    // Read matrix file:
                    title = "Read Matrix File Using Java";
                    File file = new File("/Users/plelievre/Work/Temp/junk_big_file.txt");
                    MatrixReaderDialog matrixReaderDialog = new MatrixReaderDialog(null,title,file);
                    message = matrixReaderDialog.getMessage();
                    double[][] data = matrixReaderDialog.getData();
                    if (message==null) {
                        Dialogs.inform(null,"Success!",title);
                    } else {
                        Dialogs.error(null,message,title);
                    }
                    if (data==null) {
                        Dialogs.error(null,"Data is null.",title);
                    }
                    
                    // Run external program:
                    title = "Read Matrix File Using Fortran";
                    String command = "/Users/plelievre/Work/Temp/junk_read_file";
                    ProcessBuilder processBuilder = new ProcessBuilder(command);
                    CommandRunnerDialog commandRunnerDialog = new CommandRunnerDialog(null,title,processBuilder);
                    message = commandRunnerDialog.getMessage();
                    if (message==null) {
                        Dialogs.inform(null,"Success!",title);
                    } else {
                        Dialogs.error(null,message,title);
                    }
                    
                    System.exit(0);
                    
                }
            }
        );
    }
        
}

