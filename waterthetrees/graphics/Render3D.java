package waterthetrees.graphics;

import waterthetrees.Game;

public class Render3D extends Render
{
    public double[] zRender;
    private double renderDistance = 5000.0; 

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
    public static final int RENDER_LIMIT = 500;
    public static final int MAX_BRIGHTNESS = 255;
    public static final int RED_VALUE = 16;
    public static final int GREEN_VALUE = 8;

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
        zRender = new double[width*height];
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

                zRender[x + y * width] = z;

                pixels[x + y * width] = ((xInt & BITS) * PIXEL_DENSITY) | 
                    ((yInt & BITS) * PIXEL_DENSITY) << PIXEL_SHIFT;

                if (z > RENDER_LIMIT)
                    {
                        pixels[x + y * width] = 0;
                    } 
            }
        }
    }

    public void renderDistanceLimiter()
    {
        for (int i = 0; i < width * height; i++)
        {
            int color = pixels[i];
            int brightness = (int) (renderDistance / zRender[i]);
            // brightness always ranges from 0 to 255 in graphic programming

            if (brightness < 0)
            {
                brightness = 0;
            }
            // sets minimum value for brightness to 0

            if (brightness > MAX_BRIGHTNESS)
            {
                brightness = MAX_BRIGHTNESS;
            }
            // sets maximum value for brightness to 255

            int r = (color >> RED_VALUE) & 0xff;
            // 16 is the red value shift in bits
            int g = (color >> GREEN_VALUE) & 0xff;
            // 8 is the green value shift in bits
            int b = (color) & 0xff;
            // 0xff is the hexidecimal value for 255

            r = r * brightness / MAX_BRIGHTNESS;
            g = g * brightness / MAX_BRIGHTNESS;
            b = b * brightness / MAX_BRIGHTNESS;

            pixels[i] = r << RED_VALUE | g << GREEN_VALUE | b;
            // sets ith pixel value to rgb color range
        }
    }
}
