package waterthetrees.input;



public class Controller
{
    public double x;
    // left and right motion
    public double z;
    // forward and backward motion
    public double rotation;
 
    public double xa;
    // left and right motion
    public double za;
    // forward and backward motion
    public double rotationa;

    public void tick(boolean forward, boolean left, boolean back, 
        boolean right, boolean turnLeft, boolean turnRight)
    {
        double rotationSpeed = 0.025;
        double walkSpeed = 1.0;
        double xMove = 0.0;
        double zMove= 0.0;

        if (forward)
        {
            zMove++;
            System.out.println("W");
        }

        if (left)
        {
            xMove--;
            System.out.println("A");
        }

        if (back)
        {
            zMove--;
            System.out.println("S");
        }

        if (right)
        {
            xMove++;
            System.out.println("D");
        }

        if (turnLeft)
        {
            rotationa -= rotationSpeed;
        }
        
        if (turnRight)
        {
            rotationa += rotationSpeed;
        }

        xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * 
            walkSpeed;
        za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * 
            walkSpeed;    

        x += xa;
        z += za;

        xa *= 0.1;
        za *= 0.1;

        rotation += rotationa;
        rotationa *= 0.5;

    }
}