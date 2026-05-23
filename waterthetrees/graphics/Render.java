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
 * The Render class handles assigning values to each pixel across both axis
 * within the allowed render area. 
 * 
 * Bugs: 
 * 
 * Notes: 
 * 
 * @author sqweeby
 */
public class Render
{
    public final int width;
    public final int height;
    public final int[] pixels;
    // constants to be used for pixel value assingment

    /**
     * Render is a no arg constuctor that intializes the width and height
     * values of the pixel area to be rendered, as well as a blank array for
     * pixels.
     */
    public Render()
    {
        width = 0;
        height = 0;
        pixels = new int[0];
    }

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
     * draw is a method that renders each pixel, starting from our assigned
     * offset value, starting from the first y pixel and across the x axis
     * until it reaches the last pixel. This process repeates until the last 
     * row on the last y pixel is filled.
     * 
     * @param render Render object that holds our render area instance values.
     * @param xOffset Integer value of x pixel origin on screen.
     * @param yOffset Integer value of y pixel origin on screen.
     */
    public void draw(Render render, int xOffset, int yOffset)
    {
        for (int y = 0; y < render.height; y++)
        {
            int yPixel = y + yOffset;
            // y coordinate is adjusted per loop, adding intial offset
            
            if (yPixel < 0 || yPixel >= height)
            {
                continue;
            }
            // prevents rendering pixel past allowed height of rendered area

            for (int x = 0; x < render.width; x++)
            {
                int xPixel = x + xOffset;
                // x coordinate is adjusted per loop, adding intial offset

                if (xPixel < 0 || xPixel >= width)
                {
                    continue;
                }
                // prevents rendering pixel past allowed width of rendered area

                int alpha = render.pixels[x + y * render.width];
                // intializes values for pixels with no data. 


                if (alpha > 0)
                {
                    pixels[xPixel + yPixel * width] = alpha;
                }
                // pixel is left empty rather than being rendered as black
            }
            // handles pixels rendering across x axis
        }
        // handles pixels rendering across y axis
    }
}