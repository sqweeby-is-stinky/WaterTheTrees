package waterthetrees.graphics;

import waterthetrees.Game;

public class Render3D extends Render
{
    public static final int BITS = 15;
    public static final int PIXEL_DENSITY = 20;
    public static final int PIXEL_SHIFT = 8;
    public static final double CENTER = 2.0;
    public static final double ROTATION_SPEED = 100.0;
    // higer values mean slower rotation, vice versa
    public static final double MOVEMENT_SPEED = 10.0;
    // higer values mean slower movement, vice versa
    public static final double CEILING_POSITION = 10.0;
    public static final double FLOOR_POSITION = 10.0;

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

    public void floor(Game game)
    {


        double forwardBack = game.controls.z;
        double leftRight = game.controls.x;
        // variables for movement

        double rotation = game.controls.rotation;
        double cosine = Math.cos(rotation);
        double sine = Math.sin(rotation);
        // variables for rotation

        for (int y = 0; y < height; y++)
        {
            double ceiling = (y - height / CENTER) / height;

            double z = FLOOR_POSITION / ceiling;

            if (ceiling < 0)
            {
                z = CEILING_POSITION / - ceiling;
            }
            
            for (int x = 0; x < width; x++)
            {
                double xDepth = (x - width / CENTER) / height;
                // double yDepth = z;

                xDepth *= z;

                double xRotation = xDepth * cosine + z * sine;
                double yRotation = z * cosine - xDepth * sine;

                int xInt = (int) (xRotation + leftRight);
                int yInt = (int) (yRotation + forwardBack); 
                // converts depth value into integer within bit range

                pixels[x + y * width] = ((xInt & BITS) * PIXEL_DENSITY) | 
                    ((yInt & BITS) * PIXEL_DENSITY) << PIXEL_SHIFT;

            }
        }
    }
}
