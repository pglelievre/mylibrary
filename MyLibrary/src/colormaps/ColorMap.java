package colormaps;

import java.awt.Color;
import java.awt.image.BufferedImage;
import vectors.ColorVector;

/** A class that deals with how to generate a coloured image based on a gridded set of values.
 * @author plelievre
 */
public class ColorMap {
    
    ColorVector colorVector = new ColorVector();
    double value1; // Value corresponding to the lower end of the colormap.
    double value2; // Value corresponding to the upper end of the colormap.
    boolean continuous = true; // Continuous or piecewise colormap.
    
    /** Constructor for an arbitrary ColorVector.
     * @param cv Vector of Color objects to use for the colormap.
     * @param cycles Number of times to cycle the colours.
     * @param cont Continuous (true) or piecewise (false) colormap.
     * @param v1 Value corresponding to the lower end of the colormap
     * @param v2 Value corresponding to the upper end of the colormap.
     */
    public ColorMap(ColorVector cv, int cycles, boolean cont, double v1, double v2) {
        setColors(cv);
        colorVector.cycle(cycles);
        setLimits(v1,v2);
        continuous = cont;
    }
    
    /** Constructor for canned colormaps that are named and defined at the bottom of this class.
     * @param cv Name of canned colormap.
     * @param ncycles Number of times to cycle the colours.
     * @param cont Continuous (true) or piecewise (false) colormap.
     * @param v1 Value corresponding to the lower end of the colormap
     * @param v2 Value corresponding to the upper end of the colormap.
     */
    public ColorMap(String cv, int ncycles, boolean cont, double v1, double v2) {
        switch(cv){
            case "hsv": hsv(); break;
            default: rgb(); break;
        }
        colorVector.cycle(ncycles);
        setLimits(v1,v2);
        continuous = cont;
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
    
    public final void setContinuous(boolean cont) { continuous = cont; }
    
    private boolean check() {
        if ( colorVector==null ) { return false; }
        if ( colorVector.isEmpty() ) { return false; }
        return ( colorVector.size() >= 2 );
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
        if ( value <= value1 ) { return colorVector.getFirst(); }
        if ( value >= value2 ) { return colorVector.getLast(); }
        
        double n = (double)( colorVector.size() );
        if (continuous) { n = n - 1.0; }
        int r,g,b;
        
        // Interpolate between points (value1,0) and (value2,n):
        double slope = n / ( value2 - value1 );
        double d = slope*( value - value1 );
        
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
    
    /** Converts a double array to a BufferedImage based on the current colour map information.
     * @param data 2D double array of values that is converted to a BufferedImage.
     * @return The BufferedImage object.
     */
    public BufferedImage getBufferedImage(double[][] data) {
        // Create a buffered image of the correct size:
        int nrow = data.length;
        int ncol = data[0].length;
        BufferedImage image = new BufferedImage( ncol, nrow, BufferedImage.TYPE_INT_RGB); // width, height, ...
        for ( int i=0; i<nrow; i++ ){
            for ( int j=0; j<ncol; j++ ){
                Color color = interpolate( data[i][j] );
                image.setRGB(i,j,color.getRGB());
            }
        }
        return image;
    }
    
    // BELOW ARE ALL THE CANNED COLORMAPS.
    // More can be found here:
    // https://github.com/mahdilamb/colormap
    // https://github.com/mahdilamb/colormap/tree/main/src/main/java/net/mahdilamb/colormap/reference
    
    // This one corresponds to what Mehrad originally coded.
    private void rgb() {
        colorVector.clear();
        colorVector.add(new Color( 255,   0,   0 ));
        colorVector.add(new Color( 255, 255,   0 ));
        colorVector.add(new Color(   0, 255,   0 ));
        colorVector.add(new Color(   0, 255, 255 ));
        colorVector.add(new Color(   0,   0, 255 ));
    }
    
    private void hsv() {
        colorVector.clear();
        colorVector.add(Color.RED);
        colorVector.add(new Color( 255, 167,   0 ));
        colorVector.add(new Color( 175, 255,   0 ));
        colorVector.add(new Color(   8, 255,   0 ));
        colorVector.add(new Color(   0, 255, 159 ));
        colorVector.add(new Color(   0, 183, 255 ));
        colorVector.add(new Color(   0,  16, 255 ));
        colorVector.add(new Color( 151,   0, 255 ));
        colorVector.add(new Color( 255,   0, 191 ));
        colorVector.add(Color.RED);
    }
    
}
