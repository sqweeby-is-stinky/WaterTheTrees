package src.common.waterthetrees.input;

public class Controller
{
    public double x;
    // left and right motion
    public double z;
    // forward and back motion
    public double y;
    // vertical motion
    public double rotation;
 
    public double xa;
    // left and right motion
    public double za;
    // forward and backward motion
    public double rotationa;

    public static boolean turnLeft = false;
    public static boolean turnRight = false;
    public static boolean isCrouching = false;
    public static boolean isWalking = false;
    public static boolean isRunning = false;

    public void tick(boolean forward, boolean left, boolean back, 
        boolean right, boolean jump, boolean crouch, boolean sprint)
    {
        double rotationSpeed = 0.025;
        double walkSpeed = 0.5;
        double jumpHeight = 1;
        double crouchHeight = 0.5;
        double xMove = 0.0;
        double zMove= 0.0;

        if (forward)
        {
            zMove++;
            // System.out.println("W");
            isWalking = true;
        }

        if (left)
        {
            xMove--;
            // System.out.println("A");
            isWalking = true;
        }

        if (back)
        {
            zMove--;
            // System.out.println("S");
            isWalking = true;
        }

        if (right)
        {
            xMove++;
            // System.out.println("D");
            isWalking = true;
        }

        if (turnLeft)
        {
            rotationa -= rotationSpeed;
            isWalking = true;
        }
        
        if (turnRight)
        {
            rotationa += rotationSpeed;
            isWalking = true;
        }

        if (jump)
        {
            y += jumpHeight;

            crouch = false;
            sprint = false;
        }
        
        if (crouch)
        {
            y -= crouchHeight;
            
            zMove /= 2;
            xMove /= 2;

            jump = false;
            sprint = false;
            isCrouching = true;
        }

        if (sprint)
        {
            zMove *= 1.5;
            xMove *= 1.5;
            isRunning = true;
        }

        if (!forward && !left && !back && !right && !turnLeft && !turnRight)
        {
            isWalking = false;
        }

        if (!crouch)
        {
            isCrouching = false;
        }

        if (!sprint)
        {
            isRunning= false;
        }

        // if (forward && sprint)
        // {
        //      
        // }

        xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * 
            walkSpeed;
        za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * 
            walkSpeed;    

        x += xa;
        y *= 0.9; // height max
        z += za;

        xa *= 0.1;
        za *= 0.1;

        rotation += rotationa;
        rotationa *= 0.5;

    }
}