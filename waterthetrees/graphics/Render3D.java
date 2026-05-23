package waterthetrees.graphics;

public class Render3D extends Render
{
    public static final int BITS = 15;
    public static final double CENTER = 2.0;
    public static final double Z_DEPTH = 8.0;

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
            double ceiling = (y - height / CENTER) / height;
            double z = Z_DEPTH / ceiling;

            for (int x = 0; x < width; x++)
            {
                double depth = (x - width / CENTER) / height;

                depth *= z;

                int xInt = (int) (depth) & BITS;
                int yInt = (int) (z) & BITS;
                // converts depth value into integer within bit range

                pixels[x + y * width] = (xInt * 16) | (yInt * 16) << 8;

            }
        }
    }
}
