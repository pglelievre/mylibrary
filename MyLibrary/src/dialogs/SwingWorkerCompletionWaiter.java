package dialogs;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JDialog;
import javax.swing.SwingWorker;

/** For use with a model dialog that will exist until a SwingWorker is finished.
 * @author Peter
 * https://docs.oracle.com/javase/8/docs/api/javax/swing/SwingWorker.html#get--
 */
public class SwingWorkerCompletionWaiter implements PropertyChangeListener {
    
    private JDialog dialog;

    public SwingWorkerCompletionWaiter(JDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if ("state".equals(event.getPropertyName())
                && SwingWorker.StateValue.DONE == event.getNewValue()) {
            dialog.setVisible(false);
            dialog.dispose();
        }
    }
    
}

