///////////////////////////////////////////////////////////////////////////////
// Author(s):         sqweeby and yanzini <3
// Email:             xxcatgirlstink69xx@gmail.com
///////////////////////////////////////////////////////////////////////////////
//                       OUTSIDE SOURCES OF HELP
// Online sources: Official Java Documentation
//                 
///////////////////////////////////////////////////////////////////////////////

package waterthetrees;

import java.awt.event.KeyEvent;
// java imports

import waterthetrees.input.Controller;
// local imports

/**
 * Game
 * 
 * Bugs: 
 * 
 * @author sqweeby
 */
public class Game
{
    public int time;
    public Controller controls;

    public Game()
    {
        controls = new Controller();
    }

    /**
     * tick is a method that handles key input values per tick elapsed.
     * 
     * @param key Boolean array where true means key is pressed and false means
     * key is released
     */
    public void tick(boolean[] key)
    {
        time++;

        boolean forward = key[KeyEvent.VK_W];
        boolean left = key[KeyEvent.VK_A];
        boolean back = key[KeyEvent.VK_S];
        boolean right = key[KeyEvent.VK_D];
        // boolean sprint = key[KeyEvent.VK_SPACE];

        boolean turnLeft = key[KeyEvent.VK_LEFT];
        boolean turnRight = key[KeyEvent.VK_RIGHT];
        ////temporary
        
        controls.tick(forward, left, back, right, turnLeft, turnRight);

    }
}
