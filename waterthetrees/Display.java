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

package waterthetrees;
// for orginaizational purposes

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.awt.image.DataBufferInt;
// java imports

import waterthetrees.graphics.Screen;
// local imports

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
    // constants to be used in window creation and rendering
    public static final int MINUTE = 60;
    public static final double TICK_SECONDS = 60.0;
    public static final double NANO_SECONDS = 1e9;
    public static final double MILLI_SECONDS = 1e3;
    // constants to be used in frame counting 
    public static final String TITLE_DISPLAY= "Water The Trees Pre-Alpha 0.01";
    //// working title

    private Thread thread;
    /// allows multi-threading?
    private Screen screen;
    private Game game;
    private BufferedImage img;
    private boolean running = false;
    private int[] pixels;
    // intializing objects and values for display
    
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

        screen = new Screen(WIDTH_DISPLAY, HEIGHT_DISPLAY);
        // screen object of defined area
        game = new Game();
        // assigns game as Game object type
        img = new BufferedImage(WIDTH_DISPLAY, HEIGHT_DISPLAY, 
            BufferedImage.TYPE_INT_RGB);
        // img object within our defined window area and of RGB color values
        pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
        // stores integer values of rasterized img object values
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
        // sets thread equal to the curent instance of the Thread object
        thread.start();
        // begins thread

        System.out.println("The Trees Are Being Watered");
        //// sanity check
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
        // seconds not recorded
        double secondsPerTick = 1 / TICK_SECONDS; 
        // every second of rendering
        long previousTime = System.nanoTime(); 
        // Java system timer before game is running
        boolean ticked = false;
        
        while(running)
        {
            long currentTime = System.nanoTime(); 
            // Java system timer while game is running
            long passedTime = currentTime - previousTime; 
            // time elapsed during game after intialization

            previousTime = currentTime; 
            // updates time 
            unprocessedSeconds += passedTime / NANO_SECONDS; 
            // amount of nanoseconds passed before tick

            while (unprocessedSeconds > secondsPerTick)
            {
                tick();
                // calls tick method

                unprocessedSeconds -= secondsPerTick;
                // resets unprocessed seconds values for next tick
                ticked = true;
                tickCount++; 
                // indicates a tick has passed

                if (tickCount % MINUTE == 0)
                {
                    System.out.println(frames + " fps");
                    // prints fps per tick

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
        }
        // handles fps readout while game is running based on system time
    }

    /**
     * tick is a method 
     */
    private void tick()
    {
        game.tick();
        // passes tick method through game object
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

        Display game = new Display();
        // creates game object of Display type

        JFrame frame = new JFrame();
        // creates Java window object

        frame.add(game);
        // adds game object to JFrame to be displayed
        frame.pack();
        // makes window fit to determined sized
        frame.setTitle(TITLE_DISPLAY);
        // adds title to game window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ensures program stops running when window is closed
        // frame.setSize(WIDTH_DISPLAY, HEIGHT_DISPLAY);
        // sets window size
        frame.setLocationRelativeTo(null);
        // centers window
        frame.setResizable(false);
        // keeps size constant
        frame.setVisible(true);
        // window is visible


        System.out.println("Trees are Running...");
        // sanity check

        game.start();
    } 
}