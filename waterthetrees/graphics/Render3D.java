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

    double time = 0.0;

    public void floor()
    {
        for (int y = 0; y < height; y++)
        {
            double ceiling = (y - height / CENTER) / height;

            if (ceiling < 0)
            {
                ceiling = -ceiling;
            }

            double z = Z_DEPTH / ceiling;

            time += 0.00005;

            for (int x = 0; x < width; x++)
            {
                double xDepth = (x - width / CENTER) / height;
                double yDepth = z + time;

                xDepth *= z;

                int xInt = (int) (xDepth);
                int yInt = (int) (yDepth); 
                // converts depth value into integer within bit range

                pixels[x + y * width] = ((xInt & BITS) * 16) | ((yInt & BITS) *
                    16) << 8;

            }
        }
    }
}
