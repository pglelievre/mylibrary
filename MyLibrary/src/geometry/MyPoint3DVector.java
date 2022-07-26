package geometry;

import java.util.ArrayList;

/** A Vector of MyPoint3D objects.
 * Many of the methods in this class are wrappers for methods of the same name in the Java Vector class.
 * @author Peter Lelievre
 */
public class MyPoint3DVector {

    // -------------------- Properties -------------------

    // Favour composition over inheritence!
    private final ArrayList<MyPoint3D> vector = new ArrayList<>();

    // ------------------- Constructor ------------------

    public void MyPoint3DVector() {}

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
    
    /** Returns the list as a string.
     * @return A string with each point of the form "X = x, Y = y".
     */
    public String toStringEquals(){
        String output = "";
        for(int i = 0; i < size(); i++){
            output = output + (i+1) + ". " + this.get(i).toStringEquals() + "\n";
        }
        return output;
    }
    
}
