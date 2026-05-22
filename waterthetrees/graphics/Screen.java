///////////////////////////////////////////////////////////////////////////////
// Author(s):         sqweeby and yanzini <3
// Email:             xxcatgirlstink69xx@gmail.com
///////////////////////////////////////////////////////////////////////////////
//                       OUTSIDE SOURCES OF HELP
// Online sources: Official Java Documentation
//                 
///////////////////////////////////////////////////////////////////////////////

package waterthetrees.graphics;

import java.util.Random;
// java imports

import waterthetrees.Game;
// local imports

/**
 * Screen
 * 
 * Bugs: N/A
 * 
 * @author sqweeby
 */
public class Screen extends Render
{
    private Render test;
    public static final int X_TEST = 256;
    public static final int Y_TEST = 256;
    public static final int CENTER = 2;
    public static final int WAVELENGTH = 2000;
    public static final int AMPLITUDE = 200;
    public static final int CYCLES = 2;
    public static final int TRAIL = 100;
    public static final int MULTIPLYER = 10;
    public static final double REFRESH = 2000.0;

    /**
     * Screen is a constructor
     * 
     * @param height
     * @param width
     */
    public Screen(int width, int height)
    {
        super(width, height);

        Random random = new Random();
        test = new Render(X_TEST, Y_TEST);

        for (int i = 0; i < X_TEST * Y_TEST; i++)
        {
            test.pixels[i] = random.nextInt() * (random.nextInt(5) / 4);
        }
    }

    /**
     * render is a method
     */
    public void render(Game game)
    {
        for (int i = 0; i < width * height; i++)
        {
            pixels[i] = 0;
            // removes ghost pixels
        }

        for (int i = 0; i < TRAIL; i++)
        {
            
            int xAnimate = (int) (Math.sin((game.time + i) % REFRESH / 
                WAVELENGTH) * TRAIL);
            int xAnimate2 = (int) (Math.sin((System.currentTimeMillis()) % 
                REFRESH / WAVELENGTH * Math.PI * CYCLES) * AMPLITUDE);
            // sine integer values per millisecond for x axis of animation
            int yAnimate = (int) (Math.cos((game.time + i) % REFRESH / 
                WAVELENGTH) * TRAIL);
            int yAnimate2 = (int) (Math.cos((System.currentTimeMillis()) % 
                REFRESH / WAVELENGTH * Math.PI * CYCLES) * AMPLITUDE);
            // cosine integer values per millisecond for y axis of animation

            draw(test, (width - X_TEST) / CENTER + xAnimate2, (height - Y_TEST) 
                / CENTER - yAnimate2);
            // draws pixels from center of screen and animates along two axis
        }
    }
}