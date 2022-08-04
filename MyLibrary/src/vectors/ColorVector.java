package vectors;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

/** A Vector of Color objects.
 * All of the methods in this class are wrappers for methods of the same name in the Java Vector class.
 * @author Peter Lelievre
 */
public class ColorVector {

    // -------------------- Properties -------------------

    // Favour composition over inheritence!
    private ArrayList<Color> list = new ArrayList<>();

    // ------------------- Constructors ------------------

    public ColorVector() {}
    
    public ColorVector(Color[] array) {
        list = new ArrayList<>(Arrays.asList(array));
    }

    // ------------------- Deep Copy ------------------
    
    /*
    public ColorVector deepCopy() {
        ColorVector cv = new ColorVector();
        for (int i=0 ; i<size() ; i++ ) {
            cv.add(get(i));
        }
        return cv;
    }
    */

    // -------------------- Getters -------------------
    
    public boolean isEmpty() { return list.isEmpty(); }
    
    public int size() { return list.size(); }

    public Color get(int i) { return list.get(i); }
    public Color getFirst() { return list.get(0); }
    public Color getLast() { return list.get(size()-1); }

    public int getRed(int i) { return list.get(i).getRed(); }
    public int getGreen(int i) { return list.get(i).getGreen(); }
    public int getBlue(int i) { return list.get(i).getBlue(); }
    
    public boolean isCyclic() {
        return ( getFirst().getRGB() == getLast().getRGB() );
    }

    // -------------------- Setters -------------------

    //public void set(int i,Color s) { list.set(i,s); }

    // -------------------- Public Wrappers -------------------

    public void clear() { list.clear(); }

    public void add(Color s) { list.add(s); }

    //public void add(int i, Color s) { list.add(i,s); }
    
    //public void addAll(Color[] array) { list.addAll(Arrays.asList(array)); }

    //public void remove(int i) { list.remove(i); }
    
    public void removeLast() { list.remove(size()-1); }
    
    // Cycles the colours ncycles times.
    public void cycle(int ncycles) {
        if (list.isEmpty()) { return; }
        if ( ncycles <= 1) { return; }
        // Check if colormap is cyclic (same first and last colours):
        boolean isCyclic = isCyclic();
        // If cyclic then remove the last colour now and add it back in later:
        if (isCyclic) { removeLast(); }
        // Cycle the colours:
        int n = size();
        for ( int c=0 ; c<ncycles ; c++ ) { // loop over the cycles
            for ( int i=0 ; i<n ; i++ ) { // loop over the colours
                list.add(get(i));
            }
        }
        // May need to add the last colour back in:
        if (isCyclic) { add( getFirst() ); }
    }
    
    // -------------------- Public Methods -------------------
    
}