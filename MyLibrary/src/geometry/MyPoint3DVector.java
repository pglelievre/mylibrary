package geometry;

import fileio.FileUtils;
import fileio.SessionIO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;

/** A Vector of MyPoint3D objects.
 * Many of the methods in this class are wrappers for methods of the same name in the Java Vector class.
 * @author Peter Lelievre
 */
public class MyPoint3DVector implements SessionIO {

    // -------------------- Properties -------------------

    // Favour composition over inheritence!
    private final ArrayList<MyPoint3D> vector = new ArrayList<>();

    // ------------------- Constructor ------------------

    public MyPoint3DVector() {}

    // -------------------- Public Methods -------------------

    /** Clears the vector. */
    public void clear() {
        vector.clear();
    }
    
    /** Returns the size of the vector.
     * @return The size of the vector.
     */
    public int size() {
        return vector.size();
    }

    /** Returns a specified element of the vector.
     * @param i The index of the requested element.
     * @return The specified element of the vector.
     */
    public MyPoint3D get(int i) {
        return vector.get(i);
    }

    /** Adds an element to the end of the vector.
     * @param p The MyPoint2D object to add to the end of the vector.
     */
    public void add(MyPoint3D p) {
        vector.add(p);
    }
    
    /** Removes an element from the vector.
     * @param i The index of the element to remove.
     */
    public void remove(int i) {
        vector.remove(i);
    }
    
    /** Returns the list as a string. The x and y values are cast as integers.
     * @return A string with each point of the form "X = x, Y = y, Z = y".
     */
    public String toStringEquals(){
        String output = "";
        for(int i = 0; i < size(); i++){
            output = output + (i+1) + ". " + this.get(i).toStringEquals() + "\n";
        }
        return output;
    }
    
    /** Returns the list as a string. The x and y values are cast as integers.
     * @param sx String to use for x before the equals sign.
     * @param sy String to use for y before the equals sign.
     * @param sz String to use for z before the equals sign.
     * @return A string with each point of the form "[sx] = x, [sy] = y, [sz] = y".
     */
    public String toStringEquals(String sx, String sy, String sz){
        String output = "";
        for(int i = 0; i < size(); i++){
            output = output + (i+1) + ". " + this.get(i).toStringEquals(sx,sy,sz) + "\n";
        }
        return output;
    }
    
    // -------------------- SectionIO Methods --------------------

    @Override
    public boolean writeSessionInformation(BufferedWriter writer) {
        
        // Write the number of points:
        String textLine = Integer.toString(size());
        if (!FileUtils.writeLine(writer,textLine)) { return false; }
        
        // Write the points:
        for ( int i=0 ; i<size() ; i++ ) {
            MyPoint3D p = get(i);
            textLine = p.toStringSpaces();
            if (!FileUtils.writeLine(writer,textLine)) { return false; }
        }
        
        // Return successfully:
        return true;
        
    }

    @Override
    public String readSessionInformation(BufferedReader reader, boolean merge) {
        
        // Clear the object:
        clear();
        
        // Read the number of points:
        String textLine = FileUtils.readLine(reader);
        if (textLine==null) { return "Reading number of 3D points."; }
        textLine = textLine.trim();
        String[] ss = textLine.split("[ ]+");
        if (ss.length<1) { return "Not enough values on number of 3D points line."; }
        int np;
        try {
            np = Integer.parseInt(ss[0].trim()); // converts to integer
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { return "Parsing number of 3D points."; }

        // Read the points:
        for ( int i=0 ; i<np; i++ ) {
            textLine = FileUtils.readLine(reader);
            if (textLine==null) { return "Reading a 3D point."; }
            textLine = textLine.trim();
            ss = textLine.split("[ ]+");
            if (ss.length<3) { return "Not enough values on 3D point line."; }
            double x,y,z;
            try {
                x = Double.parseDouble(ss[0].trim()); // converts to double
                y = Double.parseDouble(ss[1].trim()); // converts to double
                z = Double.parseDouble(ss[2].trim()); // converts to double
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { return "Parsing a 3D point."; }
            MyPoint3D p = new MyPoint3D(x,y,z);
            add(p);
        }
        
        // Return successfully:
        return null;
        
    }
    
}
