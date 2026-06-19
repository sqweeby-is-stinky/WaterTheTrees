///////////////////////////////////////////////////////////////////////////////
// Author(s):         sqweeby and yanzini <3
// Email:             xxcatgirlstink69xx@gmail.com
///////////////////////////////////////////////////////////////////////////////
//                       OUTSIDE SOURCES OF HELP
// Online sources: Official Java Documentation
//                 JFrame Documentation
//                 https://docs.oracle.com
//                 /javase/8/docs/api/javax/swing/JFrame.html
//                 Canvas Documentation
//                 https://docs.oracle.com
//                 /javase/8/docs/api/java/awt/Canvas.html
///////////////////////////////////////////////////////////////////////////////

package src.common.waterthetrees;
// for orginaizational purposes

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;
// java imports

import src.common.waterthetrees.graphics.Screen;
import src.common.waterthetrees.input.*;

/**
 * Display is the main class that handles creating a Java based window and
 * allows said window to run and display any rendered pixels, until the window 
 * is closed.
 * 
 * Bugs: N/A
 * 
 * Notes: Hover over each of the imported packages to see documentation
 * 
 * @author sqweeby
 * @author yanzini
 */
public class Display extends Canvas implements Runnable
{
    // canvas is a java component for displaying windows
    // implements runnable interface

    public static final long serialVersionUID = 1L;

    public static final int WIDTH_DISPLAY = 800;
    public static final int HEIGHT_DISPLAY = 600;
    public static final int GAME_DIMENSIONS = 3;
    public static final int MOUSE_DIMENSIONS = 16;
    // constants to be used in window creation and rendering
    private static final int FONT_STYLE = 1; 
    // 0 = plain text, 1 = bold text, 2 = italic text, 3 = bold and italic text
    private static final int FONT_SIZE = 12;
    private static final int FPS_X_LOC = 10;
    private static final int FPS_Y_LOC = 20;
    // constants for strings
    public static final int MINUTE = 60;
    public static final double TICK_SECONDS = 60.0;
    public static final double NANO_SECONDS = 1e9;
    public static final double MILLI_SECONDS = 1e3;
    // constants to be used in frame counting 
    public static final String TITLE_DISPLAY = 
        "Water The Trees Pre-Alpha 0.0.1";
    //// working title
    public static final String FPS_COUNT = " fps";

    private Thread thread;
    private Screen screen;
    private Game game;
    private BufferedImage img;
    private boolean running = false;
    private int[] pixels;
    private InputHandler input;
    // intializing objects and values for display

    private int deltaX = 0;
    private int initialX = 0;
    private int fps;
    
    /**
     * Display is the main no-arg constuctor that assigns values to the game
     * window such as total window area and area to be rendered. 
     */
    public Display()
    {
        Dimension size = new Dimension(WIDTH_DISPLAY, HEIGHT_DISPLAY);
        // Dimension object that defines the display area
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        // display area restrictions
        //// this may not be necessary

        screen = new Screen(WIDTH_DISPLAY, HEIGHT_DISPLAY);
        // screen object of defined area
        game = new Game();
        img = new BufferedImage(WIDTH_DISPLAY, HEIGHT_DISPLAY, 
            BufferedImage.TYPE_INT_RGB);
        // img object within our defined window area and of RGB color values
        pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
        // stores integer values of rasterized img object values
        input = new InputHandler();

        addKeyListener(input);
        addFocusListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);
    }

    /**
     * start is a method that checks if game is running and intilizes a new
     * thread(s).
     * 
     * @return Should return print statement in console.
     */
    private void start()
    {
        if (running) 
            return;
        
        running = true;
        thread = new Thread(this);
        thread.start();

        System.out.println("The Trees Are Being Watered");
    }

    /**
     * stop is a method that checks if game is not running and throws exception
     * while thread(s) try to terminate.
     * 
     * @return Throws exception if game is not running.
     */
    public void stop()
    {
        if (!running)
            return;
        
        running = false;

        try
        {
            thread.join();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
        // throws exception if thread is interuptted and exits program
    }

    /**
     * run is a method that keeps track of the current fps while the pixels are
     * rendered on the game window. Uses system time to a nanosecond degree of
     * accuracy for fps calculation.
     */
    public void run()
    {
        int frames = 0; 
        int tickCount = 0;
        double unprocessedSeconds = 0; 
        double secondsPerTick = 1 / TICK_SECONDS; 
        long previousTime = System.nanoTime(); 
        // Java system timer before game is running
        boolean ticked = false;
        
        while(running)
        {
            long currentTime = System.nanoTime(); 
            // Java system timer while game is running
            long passedTime = currentTime - previousTime; 

            previousTime = currentTime; 
            unprocessedSeconds += passedTime / NANO_SECONDS; 
            // amount of nanoseconds passed before tick

            while (unprocessedSeconds > secondsPerTick)
            {
                tick();

                unprocessedSeconds -= secondsPerTick;
                // resets unprocessed seconds values for next tick
                ticked = true;
                tickCount++; 

                if (tickCount % MINUTE == 0)
                {
                    fps = frames;

                    previousTime += MILLI_SECONDS;
                    // adds 1000 seconds to passed time calculations
                    //// not too sure this is necessary
                    frames = 0;
                    // resets frames to be recalculated after elapsed time
                }
                // checks if 60 ticks has elpased before printing fps value;
            }
            // checks is unprocessed time exceeds amount of seconds in tick

            if (ticked)
            {
                render();
                frames++;
            }
            // checks for tick before rendering frames

            render();
            frames++;
            // renders frames while running 

            deltaX = InputHandler.mouseX;
            // change in cursor horizontal coordinate value within game window

            if (deltaX > initialX)
            {
                //System.out.println("right");
                Controller.turnRight = true;
            }

            if(deltaX < initialX)
            {
                //System.out.println("left");
                Controller.turnLeft = true;
            }

            if(deltaX == initialX)
            {
                //System.out.println("still");
                Controller.turnLeft = false;
                Controller.turnRight = false;
            }

            initialX = deltaX;

            // System.out.println("X: " + InputHandler.mouseX + ", Y: " + 
            //     InputHandler.mouseY);
        }
        // handles fps readout and mouse movement while game is running based 
        // on system time
    }

    /**
     * tick is a method 
     */
    private void tick()
    {
        game.tick(input.key);
        // links key input to timer
    }

    /**
     * render is a method that handles Java based rendering using buffers,
     * as well as rendering and drawing pixels to the opened window area.
     * 
     * @return N/A
     */
    private void render()
    {
        BufferStrategy bs  = this.getBufferStrategy();

        if (bs == null)
            {
                createBufferStrategy(GAME_DIMENSIONS);
                // used for rendering in Java based on game dimensions
                return;
            }

        screen.render(game);
        // calls render method from Screen class using width and height values

        for (int i = 0; i < WIDTH_DISPLAY * HEIGHT_DISPLAY; i++)
        {
            pixels[i] = screen.pixels[i];
            // sets pixels array values equal to generated values in screen
        }

        Graphics g = bs.getDrawGraphics();
        // Java graphics for buffer, hold information for rendering
        g.drawImage(img, 0, 0, WIDTH_DISPLAY, HEIGHT_DISPLAY, null);
        // draws rendered pixels withing specified area within window
        g.setFont(new Font("Verdana", FONT_STYLE, FONT_SIZE));
        g.setColor(Color.white);
        g.drawString(fps + FPS_COUNT, FPS_X_LOC, FPS_Y_LOC);
        g.dispose();
        // acts as garbage collection for Graphics object
        // realocates memory used for rendering pixels
        bs.show();
        // shows most recent buffer data on screen
    }


    /**
     * Creates window and displays output of pixel rendering. 
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args)
    {

        BufferedImage cursor = new BufferedImage(MOUSE_DIMENSIONS, 
            MOUSE_DIMENSIONS, BufferedImage.TYPE_INT_ARGB);

        Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, 
            new Point(0, 0), "blank");

        Display game = new Display();

        JFrame frame = new JFrame();
        // creates Java window object


        frame.add(game);
        // adds game object to JFrame to be displayed
        frame.pack();
        frame.getContentPane().setCursor(blank);
        // cursor should not appear within window
        frame.setTitle(TITLE_DISPLAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ensures program stops running when window is closed
        // frame.setSize(WIDTH_DISPLAY, HEIGHT_DISPLAY);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);

        System.out.println("Trees are Running...");

        game.start();
    } 
}