package gui;

import fileio.FileUtils;
import fileio.SessionIO;
import java.awt.image.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import javax.imageio.*;

/** An object with an image read from a file.
 * @author Peter Lelievre
 */
public class HasImage implements SessionIO {

    // ------------------ Properties -------------------

    private File file = null; // the image file
    private BufferedImage image = null; // the image

    // ------------------ Constructor -------------------

    public HasImage() {} // required by the SessionLoader (should not be used elsewhere)
    
    public HasImage(File f) {
        // Set the file:
        file = f;
        // Load the image:
        loadImage();
    }
    
    public HasImage(File f, boolean load) {
        // Set the file:
        file = f;
        // Load the image:
        if (load) { loadImage(); }
    }
    
    public HasImage(BufferedImage im) {
        // Set the image:
        image = im;
    }
    
    private void loadImage() {
        if ( file==null ) { return; }
        try { image = ImageIO.read(file); } catch (IOException e) {}
    }
    
    // -------------------- Clearing --------------------
    
    public void clearImage() { image = null; }
    
    // -------------------- Deep Copy --------------------
    
    public HasImage deepCopy() {
        //File f = new File(this.file.toURI());
        //return new HasImage(f,false); // the image will be read from file later, as needed
        // Create new object:
        HasImage newHasImage = new HasImage();
        // Copy the file:
        if ( file != null ) {
            newHasImage.setFile( new File(this.file.toURI()) );
        }
        // Check for an image:
        if (this.image==null) { return newHasImage; }
        // Copy the image:
        ColorModel cm = this.image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        //WritableRaster raster = this.image.copyData(null);
        WritableRaster raster = this.image.copyData(this.image.getRaster().createCompatibleWritableRaster());
        newHasImage.setImage( new BufferedImage(cm, raster, isAlphaPremultiplied, null) );
        // Return the new object:
        return newHasImage;
    }

    // -------------------- Setters --------------------
    
    private void setFile(File f) {
        file = f;
    }
    private void setImage(BufferedImage im) {
        image = im;
    }

    // -------------------- Getters --------------------

    public int getWidth() {
        if (image==null) {
            return 0;
        } else {
            return image.getWidth();
        }
    }
    
    public int getHeight() {
        if (image==null) {
            return 0;
        } else {
            return image.getHeight();
        }
    }
    
    /** Returns the image associated with the file or null if the file could not be read.
     * @return  */
    public BufferedImage getImage() {
        // Try to read the image if necessary:
        if ( image==null ) {
            if ( file==null ) { return null; }
            // Try to read the image file:
            try { image = ImageIO.read(file); } catch (IOException e) {}
        }
        return image;
    }
    
    /** Returns the file.
     * @return  */
    public File getFile() { return file; }

//    // -------------------- Setters --------------------
//    
//    /** Sets the image file and reads it. (This is the same as the constructor so just create a new object!)
//     * @param f */
//    public void setImageFile(File f) {
//        // Set the file:
//        file = f;
//        // Load the image:
//        try { image = ImageIO.read(file); } catch (IOException e) {}
//    }

    // -------------------- Public Methods --------------------
    
    /** Returns true if the file for this object is null or the same as the supplied file.
     * @param f
     * @return  */
    public boolean compareFile(File f) {
        if ( file==null ) { return true; }
        return ( file.compareTo(f) == 0 );
    }

    /** Returns the name of the file name (the file name minus path and extension) or null if the file is null.
     * @return  */
    public String getName() {
        if ( file==null ) { return null; }
        return FileUtils.getName(file);
    }

    /** Returns the name of the file name with extension (the file name minus path) or null if the file is null.
     * @return  */
    public String getNameExt() {
        if ( file==null ) { return null; }
        return FileUtils.getNameExt(file);
    }

    /** Returns the root of the file name (the file name minus extension) or null if the file is null.
     * @return  */
    public String getRoot() {
        if ( file==null ) { return null; }
        return FileUtils.getRoot(file);
    }

    /** Returns the file name (full absolute path + name + extension) or null if the file is null.
     * @return  */
    public String fileString() {
        if ( file==null ) { return null; }
        return file.getAbsolutePath();
    }
    
    /** Returns a string with the file URI in it, or "null" if the file is null.
     * @return  */
    public String fileURIString() {
        String s;
        if (file==null) {
            s = "null";
        } else {
            s = file.toURI().toString();
        }
        return s;
    }

    // -------------------- SessionIO Methods --------------------
    
    @Override
    public boolean writeSessionInformation(BufferedWriter writer) {
        // Write the image file name:
        String textLine = fileURIString();
        return FileUtils.writeLine(writer,textLine);
    }
    
    @Override
    public String readSessionInformation(BufferedReader reader, boolean merge) {
        
        // Read the file name:
        String textLine = FileUtils.readLine(reader);
        if (textLine==null) { return "Reading file name."; }
        textLine = textLine.trim();
        if (textLine.startsWith("null")) {
            file = null;
        } else {
            try {
                URI uri = new URI(textLine);
                file = new File(uri); // image file or .node file
            } catch (URISyntaxException e) { return "Parsing URI."; }
        }
        
        // Check the file exists:
        if (!file.exists()) { return null; } // (return successfully) the file will have to be relocated when needed
        
        // Load the image:
        try { image = ImageIO.read(file); } catch (IOException e) {}
        
        // Return successfully:
        return null;
        
    }

}