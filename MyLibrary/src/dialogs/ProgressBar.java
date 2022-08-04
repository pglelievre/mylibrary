package dialogs;
        
import java.awt.BorderLayout;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
//import java.util.concurrent.TimeUnit;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

// Simple progress bar with indication of progress.
// NOT WORKING
public class ProgressBar extends JDialog {
    private static final long serialVersionUID = 1L;
 
    private final JProgressBar bar;
    private final int limit;
    private boolean cancelled = false;
 
//    public static void main( String args[] )  {
//        
//        int n = 20;
//        ProgressBar progressBar = new ProgressBar(null,"this is the title for the test","do not close this",n);
//        for (int i = 0; i < n; i++) {
//            try {
//                TimeUnit.MILLISECONDS.sleep(200);
//            } catch (InterruptedException ex) {
//            }
//            if (progressBar.isCancelled()) {
//                break;
//            } else {
//                progressBar.fill(i);
//            }
//        }
//        progressBar.close();
//    
//    }
    
    public ProgressBar(Frame parent, String title, String message, int len) {
        
        super(parent,"");
        limit = len-1;
        JPanel panel = new JPanel();
        bar = new JProgressBar();
        bar.setValue(0);
        bar.setMinimum(0);
        bar.setMaximum(limit);
        bar.setStringPainted(true);
        JTextField text = new JTextField("");
        text.setHorizontalAlignment(JTextField.CENTER);
        text.setEditable(false);
        text.setBackground(null);
        //JButton button = new JButton("Cancel");
        //button.addActionListener(new CancelListener());
        panel.setLayout(new BorderLayout());
        panel.add(text,BorderLayout.NORTH);
        panel.add(bar,BorderLayout.SOUTH);
        add(panel);
        pack();
        int h = getHeight();
        int w1 = getWidth();
        FontMetrics fm = this.getGraphics().getFontMetrics();
        w1 = w1 + fm.stringWidth(title);
        setTitle(title);
        text.setText(message);
        pack();
        int w2 = getWidth();
        int w = Math.max(w1,w2);
        setSize(w,h);
        addWindowListener(closeWindow);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        fill(0);
        
    }
 
    public final void fill(int i) { bar.setValue(i); }
    
    public void close() {
        setVisible(false);
        dispose();
    }
  
    public boolean isCancelled() { return cancelled; }
    
//    private class CancelListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            cancelled = true;
//            setVisible(false);
//            dispose();
//        }
//    }
  
    private final WindowListener closeWindow = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            cancelled = true;
            Window w = e.getWindow();
            w.setVisible(false);
            w.dispose();
        }
    };
    
    
}
