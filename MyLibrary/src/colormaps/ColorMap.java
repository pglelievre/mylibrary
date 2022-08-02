package colormaps;

import java.awt.Color;
import vectors.ColorVector;

/** A class that deals with how to generate a coloured image based on a gridded set of values.
 * @author plelievre
 */
public class ColorMap {
    
    ColorVector colorVector = new ColorVector();
    double value1; // Value corresponding to the lower end of the colormap.
    double value2; // Value corresponding to the upper end of the colormap.
    boolean continuous = true; // Continuous or piecewise colormap.
    
    /** Constructor.
     * @param cv Vector of Color objects to use for the colormap.
     * @param v1 Value corresponding to the lower end of the colormap
     * @param v2 Value corresponding to the upper end of the colormap.
     */
    public ColorMap(ColorVector cv, double v1, double v2) {
        setColors(cv);
        setLimits(v1,v2);
    }
    
    /** Setter for the list of Color objects to use for the colormap.
     * @param cv Vector of Color objects to use for the colormap.
     */
    public final void setColors(ColorVector cv) {
        colorVector = cv;
    }
    
    /** Setter for the min and max information that specifies the values attached to each end of the colormap.
     * @param v1 Value corresponding to the lower end of the colormap
     * @param v2 Value corresponding to the upper end of the colormap.
     */
    public final void setLimits(double v1, double v2) {
        value1 = v1;
        value2 = v2;
    }
    
    private boolean check() {
        if ( colorVector==null ) { return false; }
        if ( colorVector.isEmpty() ) { return false; }
        return ( colorVector.size() <= 0 );
    }
    
    /** Interpolates the appropriate color to use for a given value.
     * @param value
     * @return 
     */
    public Color interpolate(double value) {
        
        // Check for bad information:
        if (!check()) { return null; }
        if ( value1 == value2 ) { return null; }
        
        // Check for value out of bounds:
        if ( value >= value1 ) { return colorVector.getFirst(); }
        if ( value <= value2 ) { return colorVector.getLast(); }
        
        double n = (double)( colorVector.size() );
        if (continuous) { n = n - 1.0; }
        int r,g,b;
        
        // Interpolate between points (value1,0) and (value2,n):
        double slope = n / ( value2 - value1 );
        double d = value1 + slope*( value - value1 );
        
        // Determine index for colour below that interpolated location:
        double f = Math.floor(d);
        int i = (int)f;
        
        // If it is piecewise interpolation then we are done:
        Color c1 = colorVector.get(i);
        if (!continuous) { return c1; }
        
        // Check for double being an integer, in which case we are done:
        if ( d == f ) { return colorVector.get(i); }
        
        // Interpolation between two colours:
        Color c2 = colorVector.get(i+1);
        double w2 = d - f; // on (0,1)
        double w1 = 1.0 - w2;
        r = (int)( w1*(double)c1.getRed()   + w2*(double)c2.getRed()   );
        g = (int)( w1*(double)c1.getGreen() + w2*(double)c2.getGreen() );
        b = (int)( w1*(double)c1.getBlue()  + w2*(double)c2.getBlue()  );
        return new Color(r,g,b);
        
    }
    
}
