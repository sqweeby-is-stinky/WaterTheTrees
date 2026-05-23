///////////////////////////////////////////////////////////////////////////////
// Author(s):         sqweeby and yanzini <3
// Email:             xxcatgirlstink69xx@gmail.com
///////////////////////////////////////////////////////////////////////////////
//                       OUTSIDE SOURCES OF HELP
// Online sources: Official Java Documentation
//                 
///////////////////////////////////////////////////////////////////////////////

package waterthetrees.graphics;

/**
 * Render
 * 
 * Bugs: Check Notes
 * 
 * Notes: Change and test display.HEIGHT/WIDTH_DISPLAY back to height/width to
 * see if there is any difference in performance. I WAS RIGHT
 * 
 * @author sqweeby
 */
public class Render
{
    public final int width;
    public final int height;
    public final int[] pixels;

    /**
     * Render is the main constructor that assigns width and height values for
     * the area of pixels to be genrated, as well as creating an array equal
     * in size to the rendered area.
     * 
     * @param width Integer value of display width to be rendered
     * @param height Integer value of display height to be rendered
     */
    public Render(int width, int height)
    {
        this.width = width;
        this.height = height;
        // intializing and assigning render dimension values
        pixels = new int[width * height];
        // intializing pixel array with a length of the rendering area
    }

    /**
     * draw is a method
     * 
     * @param render Render object that holds our render area instance values.
     * @param xOffset Integer value of x pixel origin on screen.
     * @param yOffset Integer value of y pixel origin on screen.
     */
    public void draw(Render render, int xOffset, int yOffset)
    {
        for (int y = 0; y < render.height; y++)
        {
            // render.height calls our Screen subclass values
            int yPixel = y + yOffset;
            
            if (yPixel < 0 || yPixel >= height)
            {
                // height calls our Display Class values
                continue;
            }

            for (int x = 0; x < render.width; x++)
            {
                int xPixel = x + xOffset;

                if (xPixel < 0 || xPixel >= width)
                {
                    continue;
                }

                int alpha = render.pixels[x + y * render.width];
                // intializes values for pixels with no data 


                if (alpha > 0)
                {
                    pixels[xPixel + yPixel * width] = alpha;
                }
            }
        }
    }
}