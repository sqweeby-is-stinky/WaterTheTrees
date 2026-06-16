package src.waterthetrees.input;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements KeyListener, FocusListener, MouseListener, 
    MouseMotionListener
{
    public boolean[] key = new boolean[68836];
    public static int mouseX;
    public static int mouseY;

    @Override
    public void mouseDragged(MouseEvent e)
    {
        // TODO Auto-generated method stub
    }

    /**
     * mouseMoved is a method that gets the horizontal and vertical coordinate
     * values from the mouse cursor, within the game window.
     * 
     * @param e Mouse movement input.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void focusGained(FocusEvent e) {
        // TODO Auto-generated method stub
    }

    /**
     * focusLost checks if user has clicked outside of window and halts game
     * movement.
     * 
     * @param e Mouse button input and location relative to game window.
     */
    @Override
    public void focusLost(FocusEvent e)
    {
        for (int i = 0; i < key.length; i++)
        {
            key[i] = false;
        }
        // sets key input to false
    }

    /**
     * keyPressed checks if user has clicked a key on keyboard.
     * 
     * @param e Keyboard key input.
     */
    @Override
    public void keyPressed(KeyEvent e) 
    {
        int keyCode = e.getKeyCode();

        if (keyCode > 0 && keyCode < key.length)
        {
            key[keyCode] = true;            
        }
        // checks if key is pressed and sets key value to true
    }

    /**
     * keyReleased checks if user has realeased a key on keyboard.
     * 
     * @param e Keyboard key no input.
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
        int keyCode = e.getKeyCode();

        if (keyCode > 0 && keyCode < key.length)
        {
            key[keyCode] = false;
        }
        // checks if key has been released and sets key value to false
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

}