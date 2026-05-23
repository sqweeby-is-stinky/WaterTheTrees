package waterthetrees.graphics;

public class Render3D extends Render
{
    public static final int BITS = 5;
    public static final int CENTER = 2;
    public static final double Z_DEPTH = 100.0;

    /**
     * Render 3D is a no args construxtor
     */
    public Render3D()
    {
        super();
    }

    /**
     * Render3D is the main 
     */
    public Render3D(int width, int height)
    {
        super(width, height);
    }

    public void floor()
    {
        for (int y = 0; y < height; y++)
        {
            double yDepth = y - height / CENTER;
            double z = Z_DEPTH / yDepth;

            for (int x = 0; x < width; x++)
            {
                double depth = x - width / CENTER;

                depth *= z;

                int xInt = (int) depth & BITS;
                // converts depth value into integer within bit range

                pixels[x + y * width] = xInt * 128;

            }
        }
    }
}
