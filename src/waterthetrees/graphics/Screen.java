///////////////////////////////////////////////////////////////////////////////
// Author(s):         sqweeby and yanzini <3
// Email:             xxcatgirlstink69xx@gmail.com
///////////////////////////////////////////////////////////////////////////////
//                       OUTSIDE SOURCES OF HELP
// Online sources: Official Java Documentation
//                 
///////////////////////////////////////////////////////////////////////////////

package src.waterthetrees.graphics;

import java.util.Random;
// java imports

import src.waterthetrees.Game;

/**
 * Screen dds
 * 
 * Bugs: N/A
 * 
 * @author sqweeby
 */
public class Screen extends Render
{
    private Render test;
    // intializing Render object
    private Render3D render;
    public static final int X_TEST = 256;
    public static final int Y_TEST = 256;
    // subject to change
    public static final int CENTER = 2;
    public static final int WAVELENGTH = 2000;
    public static final int AMPLITUDE = 200;
    public static final int CYCLES = 2;
    public static final int TRAIL = 100;
    public static final int MULTIPLYER = 10;
    public static final double REFRESH = 2000.0;
    // subject to change
    
    /**
     * Screen is a no arg constuctor that intializes values from its parent 
     * class Render as well as a Render type object.
     */
    public Screen()
    {
        super();
        this.test = new Render();
    }

    /**
     * Screen is a constructor intilizes an area for the pixels to be rendered,
     * based on the width and height values from the parent class, and assigns 
     * each pixel a random integer value. 
     * 
     * @param height
     * @param width
     */
    public Screen(int width, int height)
    {
        super(width, height);

        Random random = new Random();

        render = new Render3D(width, height);
        test = new Render(X_TEST, Y_TEST);

        for (int i = 0; i < X_TEST * Y_TEST; i++)
        {
            test.pixels[i] = random.nextInt();
        }
        // assigns random integer value for each ith pixel 
    }

    /**
     * render is a method that draws the pixels and updates the location where
     * new pixels are drawn. In other words, pixels are animated across both
     * axis/
     * 
     * @param game Game object
     */
    public void render(Game game)
    {
        for (int i = 0; i < width * height; i++)
        {
            pixels[i] = 0;
            // removes ghost pixels beyond rendered area
        }

        render.floor(game);
        render.renderDistanceLimiter();
        
        draw(render, 0, 0);
    }
}