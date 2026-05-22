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
     * Render is a mutator that takes blank array pixels and increases to the 
     * size of the total display window area
     * 
     * @param width Integer value of display width
     * @param height Integer value of display height
     */
    public Render(int width, int height)
    {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        // intializing 2D pixel array of inputted width and height 
    }

    /**
     * draw is a method
     * 
     * @param render Object of Render type
     * @param xOffset Integer value
     * @param yOffset Integer value
     */
    public void draw(Render render, int xOffset, int yOffset)
    {
        // System.out.println("height: " + render.height);
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