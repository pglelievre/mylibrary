package dialogs;

/**
 *
 * @author plelievre
 */
public class MatrixReaderReturnObject {
    
    public String message = null;
    public double[][] data = null;
    public MatrixReaderReturnObject() {}
    public MatrixReaderReturnObject(String s) { message = s; }
    public MatrixReaderReturnObject(double[][] d) { data = d; }
    
}
